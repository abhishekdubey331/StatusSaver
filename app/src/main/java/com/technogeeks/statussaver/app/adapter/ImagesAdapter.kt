package com.technogeeks.statussaver.app.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.extensions.isImage
import com.technogeeks.statussaver.app.extensions.loadImage
import com.technogeeks.statussaver.app.extensions.openFile
import com.technogeeks.statussaver.library.android.utils.FileManagerUtil
import java.io.File

class ImagesAdapter(private val context: Context, private val imageList: List<File>) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: AppCompatImageView = view.findViewById(R.id.status_image)
        val downloadButton: AppCompatImageView = view.findViewById(R.id.downloadButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_image, parent, false))

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val file = imageList[position]
        val imagePath = file.absolutePath
        val myBitmap: Bitmap = BitmapFactory.decodeFile(imagePath)
        when {
            file.isImage() -> {
                holder.imageView.loadImage(Uri.fromFile(file))
            }
            else -> {
                holder.imageView.setImageBitmap(myBitmap)
            }
        }
        holder.downloadButton.setOnClickListener {
            FileManagerUtil
                .saveImage(context, myBitmap, file.name) {
                    it?.let {
                        holder
                            .downloadButton
                            .setImageResource(R.drawable.ic_downloaded_file)
                    }
                }
        }
        holder.imageView.setOnClickListener {
            file.openFile(context)
        }
        if (FileManagerUtil.isFileDownloaded(fileName = file.name).exists()) {
            holder.downloadButton.setImageResource(R.drawable.ic_downloaded_file)
            holder.downloadButton.setOnClickListener(null)
        }
    }
}