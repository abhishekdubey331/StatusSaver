package com.technogeeks.statussaver.app.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.extensions.isGIF
import com.technogeeks.statussaver.app.extensions.isImage
import java.io.File

class DownloadsAdapter(private val context: Context, private val imageList: List<File>) :
    RecyclerView.Adapter<DownloadsAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: AppCompatImageView = view.findViewById(R.id.status_image)
        val shareButton: AppCompatImageView = view.findViewById(R.id.shareButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            LayoutInflater.from(context).inflate(R.layout.custom_download_image, parent, false)
        )

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val file = imageList[position]
        val imagePath = file.absolutePath
        val myBitmap: Bitmap = BitmapFactory.decodeFile(imagePath)
        when {
            file.isImage() -> {
                holder.imageView.setImageBitmap(myBitmap)
            }
            file.isGIF() -> {
                Glide.with(context)
                    .load(myBitmap)
                    .asGif()
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView)
            }
            else -> {
                holder.imageView.setImageBitmap(myBitmap)
            }
        }
        holder.shareButton.setOnClickListener {

        }
    }
}