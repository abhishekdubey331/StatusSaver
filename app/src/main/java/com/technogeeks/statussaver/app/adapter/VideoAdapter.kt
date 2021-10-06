package com.technogeeks.statussaver.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.extensions.loadVideoFrame
import com.technogeeks.statussaver.app.extensions.makeVisible
import com.technogeeks.statussaver.app.extensions.playVideoInGallery
import com.technogeeks.statussaver.library.android.utils.FileManagerUtil
import java.io.File

class VideoAdapter(private val context: Context, private val imageList: List<File>) :
    RecyclerView.Adapter<VideoAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: AppCompatImageView = view.findViewById(R.id.status_image)
        val videoPlayButton: AppCompatImageView = view.findViewById(R.id.video_play_button)
        val downloadButton: AppCompatImageView = view.findViewById(R.id.downloadButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_image, parent, false))

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val file = imageList[position]
        holder.videoPlayButton.makeVisible()
        holder.downloadButton.setOnClickListener {
            FileManagerUtil.copyFile(file, context) {
                holder.downloadButton.setImageResource(R.drawable.ic_downloaded_file)
            }
        }
        if (FileManagerUtil.isFileDownloaded(fileName = file.name).exists()) {
            holder.downloadButton.setImageResource(R.drawable.ic_downloaded_file)
            holder.downloadButton.setOnClickListener(null)
        }
        holder.imageView.loadVideoFrame(file)
        holder.imageView.setOnClickListener {
            imageList[holder.adapterPosition].playVideoInGallery(context)
        }
    }
}