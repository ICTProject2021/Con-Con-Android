package com.example.a2021ictproject.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun String.getRequestBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaTypeOrNull())

fun Uri.getImageBody(name: String, contentResolver: ContentResolver): MultipartBody.Part {
    val cursor: Cursor = contentResolver.query(this, null, null, null, null, null)!!
    cursor.moveToNext()

    val path = cursor.getString(cursor.getColumnIndex("_data"))
    cursor.close()

    val imageFile = File(path)

    return MultipartBody.Part.createFormData(
        name, imageFile.name, imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    )
}
