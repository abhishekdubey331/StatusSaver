package com.technogeeks.statussaver.app.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
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
        binding.logoImv.loadImage(R.drawable.splash_logo)
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, BaseActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }
}