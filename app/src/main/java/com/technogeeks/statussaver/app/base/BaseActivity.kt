package com.technogeeks.statussaver.app.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.databinding.ActivityBaseBinding
import com.technogeeks.statussaver.app.extensions.toast
import com.technogeeks.statussaver.app.navigation.TabManager

class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val tabManager: TabManager by lazy { TabManager(this) }
    private var doubleBackToExitPressedOnce = false
    lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            tabManager.currentController = tabManager.navHomeController
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tabManager.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tabManager.onRestoreInstanceState(savedInstanceState)
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        tabManager.supportNavigateUpTo()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        toast(getString(R.string.back_press_again))
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        tabManager.switchTab(menuItem.itemId)
        return true
    }
}