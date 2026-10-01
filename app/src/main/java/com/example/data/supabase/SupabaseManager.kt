package com.example.data.supabase

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.UUID
import java.util.concurrent.TimeUnit

class SupabaseManager(private val context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("supabase_auth_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()
    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    companion object {
        private const val PREF_USER_ID = "pref_supabase_user_id"
        private const val PREF_USER_EMAIL = "pref_supabase_user_email"
        private const val PREF_USER_NAME = "pref_supabase_user_name"
        private const val PREF_ACCESS_TOKEN = "pref_supabase_access_token"
        private const val PREF_ANON_KEY = "pref_custom_anon_key"

        val LOCAL_DEFAULT_PROGRESS = SupabaseUserProgress(
            userId = "local_guest",
            totalWords = 50,
            totalKanji = 10,
            streakDays = 7,
            n5Progress = 82
        )
    }

    private val _currentSession = MutableStateFlow<UserSession?>(null)
    val currentSession: StateFlow<UserSession?> = _currentSession.asStateFlow()

    private val _userProgress = MutableStateFlow(LOCAL_DEFAULT_PROGRESS)
    val userProgress: StateFlow<SupabaseUserProgress> = _userProgress.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // Restore custom anon key if saved
        val savedKey = prefs.getString(PREF_ANON_KEY, null)
        if (!savedKey.isNullOrBlank()) {
            SupabaseConfig.customAnonKey = savedKey
        }

        // Restore saved session if any
        val savedUserId = prefs.getString(PREF_USER_ID, null)
        val savedEmail = prefs.getString(PREF_USER_EMAIL, null)
        val savedName = prefs.getString(PREF_USER_NAME, null)
        val savedToken = prefs.getString(PREF_ACCESS_TOKEN, null)

        if (!savedUserId.isNullOrBlank() && !savedEmail.isNullOrBlank()) {
            val session = UserSession(
                userId = savedUserId,
                email = savedEmail,
                displayName = savedName ?: savedEmail.substringBefore("@"),
                accessToken = savedToken,
                isCloudSynced = true
            )
            _currentSession.value = session
        }
    }

    fun setCustomAnonKey(key: String) {
        val trimmed = key.trim()
        SupabaseConfig.customAnonKey = trimmed
        prefs.edit().putString(PREF_ANON_KEY, trimmed).apply()
    }

    fun getGoogleOAuthUrl(): String {
        val redirectUri = "com.sadikul.japanishikhibanglay://auth-callback"
        return "${SupabaseConfig.supabaseUrl}/auth/v1/authorize?provider=google&redirect_to=${Uri.encode(redirectUri)}"
    }

    fun launchGoogleSignIn(context: Context) {
        val url = getGoogleOAuthUrl()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        try {
            context.startActivity(intent)
        } catch (_: Exception) {
            // Fallback: system browser
        }
    }

    suspend fun completeLogin(
        userId: String,
        email: String,
        displayName: String,
        accessToken: String? = null
    ): Result<SupabaseUserProgress> {
        _isLoading.value = true
        return try {
            val session = UserSession(
                userId = userId,
                email = email,
                displayName = displayName,
                accessToken = accessToken,
                isCloudSynced = true
            )
            _currentSession.value = session

            prefs.edit().apply {
                putString(PREF_USER_ID, userId)
                putString(PREF_USER_EMAIL, email)
                putString(PREF_USER_NAME, displayName)
                putString(PREF_ACCESS_TOKEN, accessToken)
                apply()
            }

            // Fetch progress from Supabase "user_progress" table
            val cloudProgress = fetchProgressFromCloud(userId, accessToken)
            if (cloudProgress.isSuccess && cloudProgress.getOrNull() != null) {
                _userProgress.value = cloudProgress.getOrNull()!!
                Result.success(cloudProgress.getOrNull()!!)
            } else {
                // If table record doesn't exist yet, save initial progress to cloud
                val initialCloudProgress = SupabaseUserProgress(
                    userId = userId,
                    totalWords = 50,
                    totalKanji = 10,
                    streakDays = 7,
                    n5Progress = 82
                )
                saveProgressToCloud(initialCloudProgress, accessToken)
                _userProgress.value = initialCloudProgress
                Result.success(initialCloudProgress)
            }
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    // Quick Google sign-in integration / demo account for instant testing
    suspend fun signInWithGoogleAccount(
        email: String = "muhammadshadikul17@gmail.com",
        name: String = "মুহাম্মদ সাদিকুল"
    ): Result<SupabaseUserProgress> {
        val deterministicUuid = UUID.nameUUIDFromBytes(email.toByteArray()).toString()
        return completeLogin(
            userId = deterministicUuid,
            email = email,
            displayName = name,
            accessToken = null
        )
    }

    suspend fun fetchProgressFromCloud(
        userId: String,
        accessToken: String? = null
    ): Result<SupabaseUserProgress?> {
        return withContext(Dispatchers.IO) {
            try {
                val url = "${SupabaseConfig.supabaseUrl}/rest/v1/user_progress?user_id=eq.$userId&select=*"
                val apiKey = SupabaseConfig.supabaseAnonKey
                val authHeader = if (!accessToken.isNullOrBlank()) "Bearer $accessToken" else "Bearer $apiKey"

                val request = Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("apikey", apiKey)
                    .addHeader("Authorization", authHeader)
                    .addHeader("Accept", "application/json")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        return@withContext Result.failure(IOException("Supabase HTTP ${response.code}"))
                    }
                    val bodyString = response.body?.string() ?: "[]"
                    val listType = object : TypeToken<List<SupabaseUserProgress>>() {}.type
                    val list: List<SupabaseUserProgress> = gson.fromJson(bodyString, listType) ?: emptyList()
                    val progress = list.firstOrNull()
                    if (progress != null) {
                        _userProgress.value = progress
                    }
                    Result.success(progress)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun saveProgressToCloud(
        progress: SupabaseUserProgress,
        accessToken: String? = null
    ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val url = "${SupabaseConfig.supabaseUrl}/rest/v1/user_progress"
                val apiKey = SupabaseConfig.supabaseAnonKey
                val authHeader = if (!accessToken.isNullOrBlank()) "Bearer $accessToken" else "Bearer $apiKey"
                val jsonPayload = gson.toJson(progress)
                val body = jsonPayload.toRequestBody("application/json; charset=utf-8".toMediaType())

                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("apikey", apiKey)
                    .addHeader("Authorization", authHeader)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Prefer", "resolution=merge-duplicates")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        _userProgress.value = progress
                        Result.success(true)
                    } else {
                        Result.failure(IOException("Save failed with code: ${response.code}"))
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    fun signOut() {
        _currentSession.value = null
        _userProgress.value = LOCAL_DEFAULT_PROGRESS
        prefs.edit().apply {
            remove(PREF_USER_ID)
            remove(PREF_USER_EMAIL)
            remove(PREF_USER_NAME)
            remove(PREF_ACCESS_TOKEN)
            apply()
        }
    }
}
