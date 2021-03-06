package com.technogeeks.statussaver.app.views

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.adapter.ImagesAdapter
import com.technogeeks.statussaver.app.base.BaseActivity
import com.technogeeks.statussaver.app.base.BaseFragment
import com.technogeeks.statussaver.app.databinding.FragmentImagesBinding
import com.technogeeks.statussaver.app.extensions.gone
import com.technogeeks.statussaver.app.extensions.isImage
import com.technogeeks.statussaver.app.extensions.makeVisible
import com.technogeeks.statussaver.library.android.utils.FileManagerUtil
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.io.File

class ImagesFragment : BaseFragment(R.layout.fragment_images) {

    private lateinit var imagesAdapter: ImagesAdapter
    private val binding by viewBinding(FragmentImagesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStatus()
        reloadDataSetObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun reloadDataSetObserver() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).resetData().observe(viewLifecycleOwner, {
                if (it) {
                    getStatus()
                }
            })
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.emptyUi.makeVisible()
            binding.tvNoContent.makeVisible()
            binding.tvNoContent.text = "${getString(R.string.app_name)} is not available in your region yet. It will be available soon."
        }
    }

    private fun getStatus() {
        when {
            FileManagerUtil.STATUS_DIRECTORY.exists() -> {
                val imageFiles =
                    FileManagerUtil.STATUS_DIRECTORY.listFiles()
                        ?.filter { s -> s.isImage() }
                handleData(imageFiles)
            }
            FileManagerUtil.STATUS_DIRECTORY_NEW.exists() -> {
                val imageFiles =
                    FileManagerUtil.STATUS_DIRECTORY_NEW.listFiles()
                        ?.filter { s -> s.isImage() }
                handleData(imageFiles)
            }
            else -> binding.emptyUi.makeVisible()
        }
    }

    private fun handleData(list: List<File>?) {
        if (list.isNullOrEmpty()) {
            binding.tvNoContent.makeVisible()
            binding.emptyUi.makeVisible()
        } else {
            binding.emptyUi.gone()
            binding.tvNoContent.gone()
            setUpRecyclerView(list)
        }
    }

    private fun setUpRecyclerView(list: List<File>) {
        imagesAdapter = ImagesAdapter(requireContext(), list)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.imagesRecycler.layoutManager = staggeredGridLayoutManager
        binding.imagesRecycler.adapter = imagesAdapter
    }
}