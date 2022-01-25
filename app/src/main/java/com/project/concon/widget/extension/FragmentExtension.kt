package com.project.concon.widget.extension

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import com.project.concon.R

fun Fragment.startActivity(activity: Class<*>) {
    requireActivity().startActivity(activity)
}

fun Fragment.startActivityWithFinish(activity: Class<*>) {
    requireActivity().startActivityWithFinish(activity)
}

private var progressDialog: AppCompatDialog? = null

fun Fragment.showProgress() {
    if (progressDialog == null) progressDialog = AppCompatDialog(requireActivity())

    progressDialog!!.apply {
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.layout_dialog_loading)
        show()
    }
}

fun Fragment.dismissProgress() {
    if (progressDialog != null) {
        if (progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }
}