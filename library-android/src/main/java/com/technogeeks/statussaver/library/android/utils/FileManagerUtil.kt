package com.technogeeks.statussaver.library.android.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.NonNull
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


object FileManagerUtil {

    val STATUS_DIRECTORY = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "WhatsApp/Media/.Statuses"
    )

    val DOWNLOADS_DIRECTORY = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "Pictures/Status Saver/"
    )


    val STATUS_DIRECTORY_NEW = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses"
    )

    fun isFileDownloaded(fileName: String) = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "Pictures/Status Saver/$fileName"
    )

    @Throws(IOException::class)
    fun saveImage(
        context: Context,
        bitmap: Bitmap,
        @NonNull folderName: String,
        @NonNull fileName: String,
        listener: (Uri?) -> Unit
    ) {
        var fos: OutputStream? = null
        var imageFile: File? = null
        var imageUri: Uri? = null
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver: ContentResolver = context.contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + folderName
                )
                imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                if (imageUri == null) throw IOException("Failed to create new MediaStore record.")
                fos = resolver.openOutputStream(imageUri)
            } else {
                val imagesDir = File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                    ).toString() + File.separator + folderName
                )
                if (!imagesDir.exists()) imagesDir.mkdir()
                imageFile = File(imagesDir, "$fileName.png")
                fos = FileOutputStream(imageFile)
            }
            if (!bitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    fos
                )
            ) throw IOException("Failed to save bitmap.")
            fos?.flush()
        } finally {
            fos?.close()
        }
        if (imageFile != null) { //pre Q
            MediaScannerConnection.scanFile(context, arrayOf(imageFile.toString()), null, null)
            imageUri = Uri.fromFile(imageFile)
        }
        listener.invoke(imageUri)
    }
}