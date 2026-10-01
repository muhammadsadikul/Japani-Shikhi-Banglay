package com.example.data.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientInstance {
    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SupabaseConfig.supabaseUrl,
            supabaseKey = SupabaseConfig.supabaseAnonKey
        ) {
            install(Auth)
            install(Postgrest)
        }
    }
}
