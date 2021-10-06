package com.technogeeks.statussaver.app.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.pixplicity.easyprefs.library.Prefs
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.base.BaseActivity
import com.technogeeks.statussaver.app.databinding.ActivitySplashBinding
import com.technogeeks.statussaver.app.extensions.loadImage

const val SPLASH_TIME = 1500L

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().postDelayed({
            if (Prefs.getBoolean("intro_shown", false).not()) {
                startActivity(Intent(this, IntroActivity::class.java))
            } else {
                startActivity(Intent(this, BaseActivity::class.java))
            }
            finish()
        }, SPLASH_TIME)
    }
}