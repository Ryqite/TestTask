package com.example.innowise.Presentation.Utils

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

fun downloadImage(context: Context, url: String) {

    CoroutineScope(Dispatchers.IO).launch {

        try {

            val input = URL(url).openStream()

            val downloadsDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs()
            }

            val file = File(
                downloadsDir,
                "image_${System.currentTimeMillis()}.jpg"
            )

            val output = FileOutputStream(file)

            input.copyTo(output)

            input.close()
            output.close()

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Download successful", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show()
            }

            Log.e("DOWNLOAD", e.toString())
        }
    }
}
