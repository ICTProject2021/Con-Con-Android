package com.project.concon.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.service.autofill.CustomDescription
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.FragmentActivity
import com.project.concon.R
import com.project.concon.fragment.FailDialogFragment

object MessageUtils {

    fun showDialog(context: Context, title: String, msg: String) =
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("확인") { _, _ -> }
            .setCancelable(false)
            .create()
            .show()

    fun showFailDialog(activity: FragmentActivity, content: String) {
        FailDialogFragment()
            .setContent(content)
            .show(activity.supportFragmentManager, "fail")
    }

    fun showToast(context: Context, msg: String) =
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    /** 로딩 프로그레스 다이얼로그 */
    private var progressDialog: AppCompatDialog? = null

    fun showProgress(activity: Activity) {
        if (progressDialog == null)
            progressDialog = AppCompatDialog(activity)

        progressDialog!!.apply {
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setContentView(R.layout.layout_dialog_loading)
            show()
        }
    }

    fun dismissProgress() {
        if (progressDialog != null) {
            if (progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }
}