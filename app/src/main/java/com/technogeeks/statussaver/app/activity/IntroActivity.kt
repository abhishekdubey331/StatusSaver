package com.technogeeks.statussaver.app.activity

import android.content.Intent
import android.os.Bundle
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
        supportActionBar?.hide()
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mList: MutableList<ScreenItem> = ArrayList()
        mList.add(
            ScreenItem(
                "Open WhatsApp",
                "Open WhatsApp and view the statuses you wish to download.",
                R.drawable.whatsapp
            )
        )
        mList.add(
            ScreenItem(
                "Ready To Download",
                "Now you can download the status from images/videos screen by clicking download icon.",
                R.drawable.statuses
            )
        )
        mList.add(
            ScreenItem(
                "Storage Permission",
                "Please allow storage permission on next screen. This is required to download & save the status to your device.",
                R.drawable.download
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
                } else {
                    binding.btnNext.text = getString(R.string.next)
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