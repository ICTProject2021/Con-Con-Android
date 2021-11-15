package com.project.concon.utils

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData

object ImagePicker {
    val imageList = MutableLiveData<List<Uri>>()

    fun multipleSelectStart(resultLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        resultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    fun init(it: ActivityResult) {
        if (it.resultCode == RESULT_OK) {
            try {
                val list = mutableListOf<Uri>()

                if (it.data?.clipData != null) {
                    // 다중 선택일 때
                    val count = it.data?.clipData!!.itemCount

                    for (index in 0 until count) {
                        list.add(it.data?.clipData!!.getItemAt(index).uri)
                    }
                } else if (it.data != null) {
                    // 단일 선택일 때
                    list.add(it.data!!.data!!)
                }

                imageList.value = list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getBitmap(contextResolver: ContentResolver, uri: Uri): Bitmap =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contextResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(contextResolver, uri)
        }
}
