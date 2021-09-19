package com.technogeeks.statussaver.app.navigation

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.base.BaseActivity

class TabManager(private val baseActivity: BaseActivity) {

    private val startDestinations = mapOf(
        R.id.navigation_images to R.id.categoriesFragment,
        R.id.navigation_videos to R.id.dashboardFragment,
        R.id.navigation_downloads to R.id.notificationsFragment
    )
    private var currentTabId: Int = R.id.navigation_images
    var currentController: NavController? = null
    private var tabHistory = TabHistory().apply { push(R.id.navigation_images) }

    val navHomeController: NavController by lazy {
        baseActivity.findNavController(R.id.homeTab).apply {
            graph = navInflater.inflate(R.navigation.navigation_graph_main).apply {
                startDestination = startDestinations.getValue(R.id.navigation_images)
            }
        }
    }
    private val navDashboardController: NavController by lazy {
        baseActivity.findNavController(R.id.dashboardTab).apply {
            graph = navInflater.inflate(R.navigation.navigation_graph_main).apply {
                startDestination = startDestinations.getValue(R.id.navigation_videos)
            }
        }
    }
    private val navNotificationsController: NavController by lazy {
        baseActivity.findNavController(R.id.notificationsTab).apply {
            graph = navInflater.inflate(R.navigation.navigation_graph_main).apply {
                startDestination = startDestinations.getValue(R.id.navigation_downloads)
            }
        }
    }

    private val imagesTabContainer: View by lazy { baseActivity.binding.imagesTabContainer }
    private val videosTabContainer: View by lazy { baseActivity.binding.videosTabContainer }
    private val downloadedTabContainer: View by lazy { baseActivity.binding.downloadTabContainer }

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(KEY_TAB_HISTORY, tabHistory)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            tabHistory = it.getSerializable(KEY_TAB_HISTORY) as TabHistory
            switchTab(baseActivity.binding.bottomNavigationView.selectedItemId, false)
        }
    }

    fun supportNavigateUpTo() {
        currentController?.navigateUp()
    }

    fun switchTab(tabId: Int, addToHistory: Boolean = true) {
        currentTabId = tabId

        when (tabId) {
            R.id.navigation_images -> {
                currentController = navHomeController
                invisibleTabContainerExcept(imagesTabContainer)
            }
            R.id.navigation_videos -> {
                currentController = navDashboardController
                invisibleTabContainerExcept(videosTabContainer)
            }
            R.id.navigation_downloads -> {
                currentController = navNotificationsController
                invisibleTabContainerExcept(downloadedTabContainer)
            }
        }
        if (addToHistory) {
            tabHistory.push(tabId)
        }
    }

    private fun invisibleTabContainerExcept(container: View) {
        imagesTabContainer.isInvisible = true
        videosTabContainer.isInvisible = true
        downloadedTabContainer.isInvisible = true
        container.isInvisible = false
    }

    companion object {
        private const val KEY_TAB_HISTORY = "key_tab_history"
    }
}