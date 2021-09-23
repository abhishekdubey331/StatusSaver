package com.technogeeks.statussaver.app.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.technogeeks.statussaver.app.R
import java.io.File

class ImagesAdapter(private val context: Context, private val imageList: List<File>) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: AppCompatImageView = view.findViewById(R.id.status_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_image, parent, false))

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        if (imageList[position].name.contains(".jpg")) {
            if (imageList[position].exists()) {
                val myBitmap: Bitmap = BitmapFactory.decodeFile(imageList[position].absolutePath)
                holder.imageView.setImageBitmap(myBitmap)
            }
        }
    }
}