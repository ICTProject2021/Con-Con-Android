package com.project.concon.widget.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.URI

fun String.getRequestBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaTypeOrNull())

fun Uri.getImageBody(name: String, contentResolver: ContentResolver): MultipartBody.Part {
    val cursor: Cursor = contentResolver.query(this, null, null, null, null, null)!!

    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    cursor.moveToNext()

    cursor.getString(nameIndex)
    cursor.close()

    val imageFile = File(URI(this.toString()))

    return MultipartBody.Part.createFormData(
        name, imageFile.name, imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    )
}
