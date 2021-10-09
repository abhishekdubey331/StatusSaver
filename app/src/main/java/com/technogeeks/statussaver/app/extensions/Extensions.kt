package com.technogeeks.statussaver.app.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.StrictMode
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.technogeeks.statussaver.app.R
import com.technogeeks.statussaver.app.StatusApp
import java.io.File

fun File.isVideo() = this.name.contains(".mp4")

fun File.isImage() = this.name.contains(".jpg")

fun File.openFile(context: Context) {
    val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
    StrictMode.setVmPolicy(builder.build())
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    val uri = Uri.fromFile(this)
    if (this.isImage())
        intent.setDataAndType(uri, "image/*")
    else
        intent.setDataAndType(uri, "video/*")
    context.startActivity(intent)
}

fun File.shareFile(context: Context) {
    val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
    StrictMode.setVmPolicy(builder.build())
    val uri = Uri.fromFile(this)
    val share = Intent(Intent.ACTION_SEND)
    if (this.isImage())
        share.type = "image/jpeg"
    else
        share.type = "video/mp4"
    share.putExtra(Intent.EXTRA_STREAM, uri);
    context.startActivity(share)
}

fun View.gone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun AppCompatImageView.loadImage(uri: Uri) {
    Glide.with(StatusApp.getContext()).load(uri)
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun AppCompatImageView.loadImage(imageRes: Int) {
    Glide.with(StatusApp.getContext()).load(imageRes)
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun AppCompatImageView.loadVideoFrame(file: File) {
    Glide.with(context)
        .load(file)
        .placeholder(R.drawable.placeholder)
        .into(this)
}

