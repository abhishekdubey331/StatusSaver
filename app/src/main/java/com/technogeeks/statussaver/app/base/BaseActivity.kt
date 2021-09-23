package com.technogeeks.statussaver.app.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.databinding.ActivityBaseBinding
import com.technogeeks.statussaver.app.navigation.TabManager

open class BaseActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val tabManager: TabManager by lazy { TabManager(this) }
    private var doubleBackToExitPressedOnce = false
    lateinit var binding: ActivityBaseBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.imagesTab) as NavHostFragment
        navController = navHostFragment.navController
        if (savedInstanceState == null) {
            tabManager.currentController = tabManager.navImagesController
        }
        if (!isReadStorageAllowed()) {
            requestPermission()
        }
    }

    private fun isReadStorageAllowed(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
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
        tabManager.supportNavigateUpTo(upIntent)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        showToast(getString(R.string.back_press_again))
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        tabManager.switchTab(menuItem.itemId)
        return true
    }

    private fun requestPermission() {
        permissionsBuilder(Manifest.permission.WRITE_EXTERNAL_STORAGE).build().send { result ->
            if (result.allGranted()) {
                // All the permissions are granted.
            }
        }
    }

    open fun showToast(reason: String?) {
        if (reason == null) return
        Toast.makeText(applicationContext, reason, Toast.LENGTH_LONG)
            .show()
    }
}