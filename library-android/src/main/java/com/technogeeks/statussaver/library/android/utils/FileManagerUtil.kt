package com.technogeeks.statussaver.library.android.utils

import android.os.Environment
import java.io.File

object FileManagerUtil {

    val STATUS_DIRECTORY = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "WhatsApp/Media/.Statuses"
    )

    val STATUS_DIRECTORY_NEW = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses"
    )
}