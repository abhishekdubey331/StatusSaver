package com.technogeeks.statussaver.app.views

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.adapter.DownloadsAdapter
import com.technogeeks.statussaver.app.base.BaseActivity
import com.technogeeks.statussaver.app.base.BaseFragment
import com.technogeeks.statussaver.app.databinding.FragmentImagesBinding
import com.technogeeks.statussaver.app.extensions.gone
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
        getStatus()
        reloadDataSetObserver()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.emptyUi.makeVisible()
            binding.tvNoContent.makeVisible()
            binding.tvNoContent.text = "${getString(R.string.app_name)} is not available in your region yet. It will be available soon."
        }
    }

    private fun reloadDataSetObserver() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).resetData().observe(viewLifecycleOwner, {
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
            binding.tvNoContent.gone()
            binding.emptyUi.gone()
            setUpRecyclerView(list)
        } else {
            binding.tvNoContent.makeVisible()
            binding.emptyUi.makeVisible()
        }
    }

    private fun setUpRecyclerView(list: List<File>) {
        imagesAdapter = DownloadsAdapter(requireContext(), list)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(SPAN_SIZE, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.imagesRecycler.layoutManager = staggeredGridLayoutManager
        binding.imagesRecycler.adapter = imagesAdapter
    }
}