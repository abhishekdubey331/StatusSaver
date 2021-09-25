package com.technogeeks.statussaver.app.extensions

import java.io.File

fun File.isVideo() = this.name.contains(".mp4")

fun File.isImage() = this.name.contains(".jpg")

fun File.isGIF() = this.name.contains(".gif")
