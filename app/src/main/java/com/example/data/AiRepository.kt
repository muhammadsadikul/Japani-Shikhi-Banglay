package com.example.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

data class AiRequest(
    @SerializedName("message") val message: String,
    @SerializedName("level") val level: String = "N5"
)

data class AiResponse(
    @SerializedName("reply") val reply: String? = null,
    @SerializedName("error") val error: String? = null
)

object AiRepository {
    private const val WORKER_URL = "https://japani-ai-tutor.muhammadsadikul17.workers.dev"
    private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
    private val gson = Gson()

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun sendMessage(message: String, level: String = "N5"): String {
        return withContext(Dispatchers.IO) {
            val requestPayload = AiRequest(message = message, level = level)
            val jsonBody = gson.toJson(requestPayload)
            val requestBody = jsonBody.toRequestBody(JSON_MEDIA_TYPE)

            val request = Request.Builder()
                .url(WORKER_URL)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseText = response.body?.string()?.trim() ?: ""

                    if (!response.isSuccessful) {
                        throw IOException("সার্ভার প্রতিক্রিয়া ব্যর্থ হয়েছে (${response.code})")
                    }

                    if (responseText.isEmpty()) {
                        throw IOException("সার্ভার থেকে কোনো তথ্য পাওয়া যায়নি।")
                    }

                    try {
                        val parsed = gson.fromJson(responseText, AiResponse::class.java)
                        when {
                            !parsed?.reply.isNullOrBlank() -> parsed!!.reply!!
                            !parsed?.error.isNullOrBlank() -> throw IOException(parsed!!.error!!)
                            else -> responseText
                        }
                    } catch (e: Exception) {
                        if (e is IOException) throw e
                        // Fallback to plain text if server returns direct string
                        responseText
                    }
                }
            } catch (e: Exception) {
                val errorMsg = when {
                    e.message?.contains("Unable to resolve host") == true || e.message?.contains("Failed to connect") == true ->
                        "ইন্টারনেট সংযোগ চেক করুন। সার্ভারে যুক্ত হওয়া যাচ্ছে না।"
                    e.message?.contains("timeout") == true ->
                        "সময় অতিক্রান্ত হয়েছে। পুনরায় চেষ্টা করুন।"
                    !e.message.isNullOrBlank() -> e.message!!
                    else -> "সার্ভারে ত্রুটি দেখা দিয়েছে। দয়া করে আবার চেষ্টা করুন।"
                }
                throw IOException(errorMsg)
            }
        }
    }
}
