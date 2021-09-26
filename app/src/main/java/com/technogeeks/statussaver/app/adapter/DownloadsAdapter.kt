package com.technogeeks.statussaver.app.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.extensions.*
import java.io.File

class DownloadsAdapter(private val context: Context, private val imageList: List<File>) :
    RecyclerView.Adapter<DownloadsAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: AppCompatImageView = view.findViewById(R.id.status_image)
        val shareButton: AppCompatImageView = view.findViewById(R.id.downloadButton)
        val videoPlayButton: AppCompatImageView = view.findViewById(R.id.video_play_button)
        val fileTypeTv: AppCompatTextView = view.findViewById(R.id.fileTypeTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            LayoutInflater.from(context).inflate(R.layout.custom_image, parent, false)
        )

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val file = imageList[position]
        holder.fileTypeTv.makeVisible()
        when {
            file.isImage() -> {
                holder.imageView.loadImage(Uri.fromFile(file))
                holder.fileTypeTv.text = context.getText(R.string.image)
            }
            file.isVideo() -> {
                holder.videoPlayButton.makeVisible()
                holder.imageView.loadVideoFrame(file)
                holder.fileTypeTv.text = context.getText(R.string.video)
            }
            else -> {
                holder.imageView.loadImage(Uri.fromFile(file))
            }
        }
        holder.shareButton.setOnClickListener {

        }
    }
}