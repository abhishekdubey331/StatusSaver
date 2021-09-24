package com.technogeeks.statussaver.app.views

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.adapter.VideoAdapter
import com.technogeeks.statussaver.app.base.BaseFragment
import com.technogeeks.statussaver.app.databinding.FragmentVideosBinding
import com.technogeeks.statussaver.app.extensions.isVideo
import com.technogeeks.statussaver.library.android.utils.FileManagerUtil
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.io.File

class VideosFragment : BaseFragment(R.layout.fragment_videos) {

    private lateinit var videoAdapter: VideoAdapter
    private val binding by viewBinding(FragmentVideosBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStatus()
    }

    private fun getStatus() {
        when {
            FileManagerUtil.STATUS_DIRECTORY.exists() -> {
                val imageFiles =
                    FileManagerUtil.STATUS_DIRECTORY.listFiles()
                        ?.filter { s -> s.isVideo() }
                setUpRecyclerView(imageFiles ?: listOf())
            }
            FileManagerUtil.STATUS_DIRECTORY_NEW.exists() -> {
                val imageFiles =
                    FileManagerUtil.STATUS_DIRECTORY.listFiles()
                        ?.filter { s -> s.isVideo() }
                setUpRecyclerView(imageFiles ?: listOf())
            }
            else -> showToast("Nothing Found")
        }
    }

    private fun setUpRecyclerView(list: List<File>) {
        videoAdapter = VideoAdapter(requireContext(), list)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.imagesRecycler.layoutManager = staggeredGridLayoutManager
        binding.imagesRecycler.adapter = videoAdapter
    }
}