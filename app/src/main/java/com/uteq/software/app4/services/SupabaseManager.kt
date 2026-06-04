package com.uteq.software.app4.services

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseManager {
    val client = createSupabaseClient(
        supabaseUrl = "https://hnwamldwpxezbjtcmrup.supabase.co",
        supabaseKey = "sb_secret_byEO8ZhzqgfwACTPG4h2Xw_B-4crbX4"
    ) {
        install(Postgrest)
    }
}
