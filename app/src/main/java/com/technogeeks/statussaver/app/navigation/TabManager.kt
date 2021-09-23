package com.technogeeks.statussaver.app.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.base.BaseActivity

class TabManager(private val mainActivity: BaseActivity) {

    companion object {
        private const val KEY_TAB_HISTORY = "key_tab_history"
    }

    private val startDestinations = mapOf(
        R.id.navigation_images to R.id.imagesFragment,
        R.id.navigation_videos to R.id.videosFragment,
        R.id.navigation_downloads to R.id.downloadsFragment
    )

    private val imagesTabContainer: View by lazy { mainActivity.binding.imagesTabContainer }
    private val videosTabContainer: View by lazy { mainActivity.binding.videosTabContainer }
    private val downloadedTabContainer: View by lazy { mainActivity.binding.downloadTabContainer }

    private var currentTabId: Int = R.id.navigation_images
    var currentController: NavController? = null

    private var tabHistory = TabHistory().apply { push(R.id.navigation_images) }

    val navImagesController: NavController by lazy {
        mainActivity.findNavController(R.id.imagesTab).apply {
            graph = navInflater.inflate(R.navigation.navigation_graph_main).apply {
                startDestination = startDestinations.getValue(R.id.navigation_images)
            }
        }
    }
    private val navVideosController: NavController by lazy {
        mainActivity.findNavController(R.id.videosTab).apply {
            graph = navInflater.inflate(R.navigation.navigation_graph_main).apply {
                startDestination = startDestinations.getValue(R.id.navigation_videos)
            }
        }
    }
    private val navDownloadsController: NavController by lazy {
        mainActivity.findNavController(R.id.downloadTab).apply {
            graph = navInflater.inflate(R.navigation.navigation_graph_main).apply {
                startDestination = startDestinations.getValue(R.id.navigation_downloads)
            }
        }
    }

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(KEY_TAB_HISTORY, tabHistory)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            tabHistory = it.getSerializable(KEY_TAB_HISTORY) as TabHistory

            switchTab(mainActivity.binding.bottomNavigationView.selectedItemId, false)
        }
    }

    fun supportNavigateUpTo(upIntent: Intent) {
        currentController?.navigateUp()
    }


    fun switchTab(tabId: Int, addToHistory: Boolean = true) {
        currentTabId = tabId
        when (tabId) {
            R.id.navigation_images -> {
                currentController = navImagesController
                invisibleTabContainerExcept(imagesTabContainer)
            }
            R.id.navigation_videos -> {
                currentController = navVideosController
                invisibleTabContainerExcept(videosTabContainer)
            }
            R.id.navigation_downloads -> {
                currentController = navDownloadsController
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
}