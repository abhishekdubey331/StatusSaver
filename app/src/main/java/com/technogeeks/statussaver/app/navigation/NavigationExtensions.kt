package com.technogeeks.statussaver.app.navigation

import android.annotation.SuppressLint
import android.content.Intent

private const val PACKAGE_NAME = "com.technogeeks.statussaver.app"

fun intentTo(navigatableActivity: NavigatableActivity): Intent {
    return Intent(Intent.ACTION_VIEW).setClassName(
        PACKAGE_NAME,
        navigatableActivity.className
    )
}

interface NavigatableActivity {
    val className: String
}

object Activities {
    @SuppressLint("CustomSplashScreen")
    object SplashActivity : NavigatableActivity {
        override val className = "$PACKAGE_NAME.activity.SplashActivity"
    }
}