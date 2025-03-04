package com.dave.github

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GitHubSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Application
            private set
    }

}