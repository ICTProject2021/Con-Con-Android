package com.project.concon.widget.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.google.gson.Gson
import com.project.concon.model.remote.dto.response.Prize
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun String.getRequestBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaTypeOrNull())

fun Prize.getMultipartBody(): MultipartBody.Part =
    MultipartBody.Part.createFormData("prize", Gson().toJson(this))

fun Uri.getImageBody(name: String, context: Context): MultipartBody.Part {
    val cursor: Cursor = context.contentResolver.query(this, null, null, null, null, null)!!

    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    cursor.moveToNext()

    cursor.getString(nameIndex)
    cursor.close()

    val imageFile = getFile(context, this)

    return MultipartBody.Part.createFormData(
        name, imageFile.name, imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    )
}

private fun getFile(context: Context, uri: Uri): File {
    val fileName =
        File(context.filesDir.path + File.separatorChar + queryName(context, uri))

    try {
        context.contentResolver.openInputStream(uri).apply {
            createFileFromStream(this!!, fileName)
        }
    } catch (ex: Exception) {
        Log.e("Save File", ex.message!!)
        ex.printStackTrace()
    }

    return fileName
}

private fun createFileFromStream(ins: InputStream, destination: File?) {
    try {
        FileOutputStream(destination).use { os ->
            val buffer = ByteArray(4096)
            var length: Int

            while (ins.read(buffer).also { length = it } > 0) {
                os.write(buffer, 0, length)
            }

            os.flush()
        }
    } catch (ex: Exception) {
        Log.e("Save File", ex.message!!)
        ex.printStackTrace()
    }
}

private fun queryName(context: Context, uri: Uri): String {
    val returnCursor: Cursor =
        context.contentResolver.query(uri, null, null, null, null)!!
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}