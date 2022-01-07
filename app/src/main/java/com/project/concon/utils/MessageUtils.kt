package com.project.concon.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.FragmentActivity
import com.project.concon.R
import com.project.concon.view.fragment.DialogFragmentDefault
import com.project.concon.view.fragment.DialogFragmentFail

object MessageUtils {

    /** 실패 다이얼로그 */
    fun showFailDialog(activity: FragmentActivity, content: String) {
        DialogFragmentFail(content).show(activity.supportFragmentManager, "fail")
    }

    /** 업데이트 예정 다이얼로그 */
    fun showUpdateDialog(activity: FragmentActivity, content: String) {
        DialogFragmentDefault("조금만 기다려주세요!", content).show(activity.supportFragmentManager, "update")
    }

    /** 기본 다이얼로그 (정보나 알림 띄울 때 사용) */
    fun showDefaultDialog(activity: FragmentActivity, content: String) {
        DialogFragmentDefault("알림", content)
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