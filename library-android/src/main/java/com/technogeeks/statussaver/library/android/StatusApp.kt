package com.technogeeks.statussaver.library.android

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class StatusApp : Application() {

    companion object {
        private lateinit var appContext: Context
        fun getContext(): Context {
            return appContext.applicationContext
        }
    }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}