package com.example.submissionbelajarcompose

import android.annotation.SuppressLint
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    @SuppressLint("AppOpenMissing")
    override fun onCreate() {
        super.onCreate()

    }
}
