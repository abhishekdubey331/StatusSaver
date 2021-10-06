package com.technogeeks.statussaver.app.views

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.adapter.VideoAdapter
import com.technogeeks.statussaver.app.base.BaseFragment
import com.technogeeks.statussaver.app.databinding.FragmentImagesBinding
import com.technogeeks.statussaver.app.extensions.gone
import com.technogeeks.statussaver.app.extensions.isVideo
import com.technogeeks.statussaver.app.extensions.makeVisible
import com.technogeeks.statussaver.library.android.utils.FileManagerUtil
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.io.File

class VideosFragment : BaseFragment(R.layout.fragment_images) {

    private lateinit var videoAdapter: VideoAdapter
    private val binding by viewBinding(FragmentImagesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStatus()
    }

    private fun getStatus() {
        when {
            FileManagerUtil.STATUS_DIRECTORY.exists() -> {
                val videoFiles =
                    FileManagerUtil.STATUS_DIRECTORY.listFiles()
                        ?.filter { s -> s.isVideo() }
                handleData(videoFiles)
            }
            FileManagerUtil.STATUS_DIRECTORY_NEW.exists() -> {
                val videoFiles =
                    FileManagerUtil.STATUS_DIRECTORY.listFiles()
                        ?.filter { s -> s.isVideo() }
                handleData(videoFiles)
            }
            else -> binding.emptyUi.makeVisible()
        }
    }

    private fun handleData(list: List<File>?) {
        if (list.isNullOrEmpty()) {
            binding.emptyUi.makeVisible()
        } else {
            binding.emptyUi.gone()
            setUpRecyclerView(list)
        }
    }

    private fun setUpRecyclerView(list: List<File>) {
        videoAdapter = VideoAdapter(requireContext(), list)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.imagesRecycler.layoutManager = staggeredGridLayoutManager
        binding.imagesRecycler.adapter = videoAdapter
    }
}