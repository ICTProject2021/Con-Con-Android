package com.project.concon.utils

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData

object ImagePicker {
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    val imageList = MutableLiveData<List<Uri>>()

    fun multipleSelectStart(fragment: FragmentActivity) {
        if (resultLauncher == null)
            init(fragment)

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        resultLauncher!!.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private fun init(fragment: FragmentActivity) {
        resultLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
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
    }
}