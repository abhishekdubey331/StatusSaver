package com.technogeeks.statussaver.app.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.pixplicity.easyprefs.library.Prefs
import com.technogeeks.statussaver.app.base.BaseActivity
import com.technogeeks.statussaver.library.android.R
import com.technogeeks.statussaver.library.android.databinding.ActivityIntroBinding
import com.technogeeks.statussaver.library.android.intro.IntroViewPagerAdapter
import com.technogeeks.statussaver.library.android.intro.ScreenItem

class IntroActivity : AppCompatActivity() {
    var introViewPagerAdapter: IntroViewPagerAdapter? = null
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mList: MutableList<ScreenItem> = ArrayList()
        mList.add(
            ScreenItem(
                "Plan Your Trip",
                "Choose your destination, plan your trip.\nPick the best place to your holiday",
                R.drawable.library_notification_icon
            )
        )
        mList.add(
            ScreenItem(
                "Select the Date",
                "Select the day, book your ticket. We give\nthe best price for you",
                R.drawable.library_notification_icon
            )
        )
        mList.add(
            ScreenItem(
                "Enjoy Your Trip",
                "Enjoy your holiday! Take a photo, share to\nthe world and tag me",
                R.drawable.library_notification_icon
            )
        )

        //Setup viewPager
        introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        binding.screenViewpager.adapter = introViewPagerAdapter

        //Setup tab indicator
        binding.tabIndicator.setupWithViewPager(binding.screenViewpager)

        //Button Next
        binding.btnNext.setOnClickListener {
            if (binding.screenViewpager.currentItem == 2) {
                Prefs.putBoolean("intro_shown", true)
                startActivity(Intent(this, BaseActivity::class.java))
                finish()
            } else {
                binding.screenViewpager.setCurrentItem(
                    binding.screenViewpager.currentItem.plus(1),
                    true
                )
            }
        }
        binding.tabIndicator.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == mList.size - 1) {
                    loadLastScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun loadLastScreen() {
        binding.btnNext.text = getString(com.technogeeks.statussaver.app.R.string.get_started)
    }
}