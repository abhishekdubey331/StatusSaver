package com.technogeeks.statussaver.app.views

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.adapter.DownloadsAdapter
import com.technogeeks.statussaver.app.base.BaseFragment
import com.technogeeks.statussaver.app.databinding.FragmentDownloadsBinding
import com.technogeeks.statussaver.app.extensions.isGIF
import com.technogeeks.statussaver.app.extensions.isImage
import com.technogeeks.statussaver.library.android.utils.FileManagerUtil
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.io.File

class DownloadsFragment : BaseFragment(R.layout.fragment_downloads) {

    private lateinit var imagesAdapter: DownloadsAdapter
    private val binding by viewBinding(FragmentDownloadsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStatus()
    }

    private fun getStatus() {
        when {
            FileManagerUtil.DOWNLOADS_DIRECTORY.exists() -> {
                val imageFiles =
                    FileManagerUtil.DOWNLOADS_DIRECTORY.listFiles()
                        ?.filter { s -> s.isImage() || s.isGIF() }
                setUpRecyclerView(imageFiles ?: listOf())
            }
            else -> showToast("Nothing Found")
        }
    }

    private fun setUpRecyclerView(list: List<File>) {
        imagesAdapter = DownloadsAdapter(requireContext(), list)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.imagesRecycler.layoutManager = staggeredGridLayoutManager
        binding.imagesRecycler.adapter = imagesAdapter
    }
}