package com.example.data.supabase

import com.google.gson.annotations.SerializedName

data class SupabaseUserProgress(
    @SerializedName("user_id") val userId: String,
    @SerializedName("total_words") val totalWords: Int = 50,
    @SerializedName("total_kanji") val totalKanji: Int = 10,
    @SerializedName("streak_days") val streakDays: Int = 7,
    @SerializedName("n5_progress") val n5Progress: Int = 82
)

data class UserSession(
    val userId: String,
    val email: String,
    val displayName: String,
    val accessToken: String? = null,
    val isCloudSynced: Boolean = true
)
