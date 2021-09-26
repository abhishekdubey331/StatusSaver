package com.technogeeks.statussaver.app.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.extensions.gone
import com.technogeeks.statussaver.app.extensions.makeVisible
import java.io.File

class VideoAdapter(private val context: Context, private val imageList: List<File>) :
    RecyclerView.Adapter<VideoAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: AppCompatImageView = view.findViewById(R.id.status_image)
        val videoPlayButton: AppCompatImageView = view.findViewById(R.id.video_play_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_image, parent, false))

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val file = imageList[position]
        holder.videoPlayButton.makeVisible()
        Glide.with(context)
            .load(Uri.fromFile(file))
            .into(holder.imageView)
    }
}