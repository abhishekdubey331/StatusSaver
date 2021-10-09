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
        val mList = getViewPagerContent()
        //Setup viewPager
        introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        binding.screenViewpager.adapter = introViewPagerAdapter
        //Setup tab indicator
        binding.tabIndicator.setupWithViewPager(binding.screenViewpager)
        //Button Next
        binding.btnNext.setOnClickListener {
            if (binding.screenViewpager.currentItem == 2) {
                Prefs.putBoolean(INTRO_SHOWN, true)
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

    private fun getViewPagerContent(): MutableList<ScreenItem> {
        val mList: MutableList<ScreenItem> = ArrayList()
        mList.add(
            ScreenItem(
                getString(com.technogeeks.statussaver.app.R.string.open_whats_app),
                getString(com.technogeeks.statussaver.app.R.string.open_whats_app_view_status),
                R.drawable.whatsapp
            )
        )
        mList.add(
            ScreenItem(
                getString(com.technogeeks.statussaver.app.R.string.ready_to_download),
                getString(com.technogeeks.statussaver.app.R.string.now_you_can_downlod),
                R.drawable.statuses
            )
        )
        mList.add(
            ScreenItem(
                getString(com.technogeeks.statussaver.app.R.string.storage_permission),
                getString(com.technogeeks.statussaver.app.R.string.please_allow_storage),
                R.drawable.download
            )
        )
        return mList
    }

    private fun loadLastScreen() {
        binding.btnNext.text = getString(com.technogeeks.statussaver.app.R.string.get_started)
    }
}