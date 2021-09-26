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
import android.widget.Toast
import androidx.annotation.NonNull
import com.technogeeks.statussaver.library.android.R
import java.io.*

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
                    Environment.DIRECTORY_PICTURES + File.separator + context.getString(R.string.app_name)
                )
                imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                if (imageUri == null) throw IOException("Failed to create new MediaStore record.")
                fos = resolver.openOutputStream(imageUri)
            } else {
                val imagesDir = File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                    ).toString() + File.separator + context.getString(R.string.app_name)
                )
                if (!imagesDir.exists()) imagesDir.mkdir()
                imageFile = File(imagesDir, fileName)
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

    fun copyFile(source: File, context: Context, listener: (Uri?) -> Unit) {
        if (!isFileDownloaded(source.name).exists()) {
            val inputStream: InputStream = FileInputStream(source)
            val outputStream: OutputStream = FileOutputStream(isFileDownloaded(source.name))
            val buf = ByteArray(1024)
            var len: Int = inputStream.read(buf)
            Thread {
                while (len > 0) {
                    outputStream.write(buf, 0, len)
                    len = inputStream.read(buf)
                }
                inputStream.close()
                outputStream.close()
                println("Write operation done");
            }.start()
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Already exits", Toast.LENGTH_SHORT).show()
        }
        listener.invoke(Uri.EMPTY)
    }
}