package com.example.submissionbelajarcompose.di

import com.example.submissionbelajarcompose.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

@InstallIn(SingletonComponent::class)
@Module
class SupabaseModule {
    @Provides
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            BuildConfig.SUPABASE_URL,
            BuildConfig.SUPABASE_ANON_KEY,

            ) {
            install(Postgrest)
            install(Storage)

        }
    }
}