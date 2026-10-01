package com.example.data.supabase

import com.example.BuildConfig

object SupabaseConfig {
    const val DEFAULT_URL = "https://mdleilnpottaaengffnf.supabase.co"
    const val DEFAULT_ANON_KEY = "placeholder_supabase_anon_key"

    var customAnonKey: String? = null

    val supabaseUrl: String
        get() = try {
            if (BuildConfig.SUPABASE_URL.isNotBlank()) BuildConfig.SUPABASE_URL else DEFAULT_URL
        } catch (_: Throwable) {
            DEFAULT_URL
        }

    val supabaseAnonKey: String
        get() = customAnonKey ?: try {
            if (BuildConfig.SUPABASE_ANON_KEY.isNotBlank() && BuildConfig.SUPABASE_ANON_KEY != DEFAULT_ANON_KEY) {
                BuildConfig.SUPABASE_ANON_KEY
            } else {
                DEFAULT_ANON_KEY
            }
        } catch (_: Throwable) {
            DEFAULT_ANON_KEY
        }

    val isAnonKeyConfigured: Boolean
        get() = supabaseAnonKey.isNotBlank() && supabaseAnonKey != DEFAULT_ANON_KEY
}
