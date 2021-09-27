package com.technogeeks.statussaver.app.views

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.adapter.DownloadsAdapter
import com.technogeeks.statussaver.app.base.BaseActivity
import com.technogeeks.statussaver.app.base.BaseFragment
import com.technogeeks.statussaver.app.databinding.FragmentImagesBinding
import com.technogeeks.statussaver.app.extensions.makeVisible
import com.technogeeks.statussaver.library.android.utils.FileManagerUtil
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.io.File

const val SPAN_SIZE = 2

class DownloadsFragment : BaseFragment(R.layout.fragment_images) {

    private lateinit var imagesAdapter: DownloadsAdapter
    private val binding by viewBinding(FragmentImagesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is BaseActivity) {
            (activity as BaseActivity).loadDownloadedFiles().observe(viewLifecycleOwner, {
                if (it) {
                    getStatus()
                }
            })
        }
    }

    private fun getStatus() {
        when {
            FileManagerUtil.DOWNLOADS_DIRECTORY.exists() -> {
                val files = FileManagerUtil.DOWNLOADS_DIRECTORY.listFiles()?.toList()
                handleData(files ?: listOf())
            }
            else -> binding.emptyUi.makeVisible()
        }
    }

    private fun handleData(list: List<File>) {
        if (list.isNotEmpty()) {
            binding.emptyUi.makeVisible()
        } else {
            setUpRecyclerView(list)
        }
    }

    private fun setUpRecyclerView(list: List<File>) {
        imagesAdapter = DownloadsAdapter(requireContext(), list)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(SPAN_SIZE, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.imagesRecycler.layoutManager = staggeredGridLayoutManager
        binding.imagesRecycler.adapter = imagesAdapter
    }
}