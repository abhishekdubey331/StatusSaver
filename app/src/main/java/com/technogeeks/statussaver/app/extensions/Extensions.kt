package com.technogeeks.statussaver.app.extensions

import android.net.Uri
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.technogeeks.statussaver.library.android.StatusApp
import java.io.File

fun File.isVideo() = this.name.contains(".mp4")

fun File.isImage() = this.name.contains(".jpg")

fun View.gone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun AppCompatImageView.loadImage(uri: Uri) {
    Glide.with(StatusApp.getContext()).load(uri)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun AppCompatImageView.loadImage(imageRes: Int) {
    Glide.with(StatusApp.getContext()).load(imageRes)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun AppCompatImageView.loadVideoFrame(file: File) {
    Glide.with(context)
        .load(file)
        .into(this)
}

