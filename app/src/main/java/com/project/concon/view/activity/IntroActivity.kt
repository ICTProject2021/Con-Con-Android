package com.project.concon.view.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.project.concon.R
import com.project.concon.base.BaseActivity
import com.project.concon.widget.extension.startActivityWithFinish
import com.project.concon.widget.utils.PreferenceUtils.token

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        if (token != null) startActivityWithFinish(MainActivity::class.java)
        else verifyPermission()
    }

    // Permission 체크 (이미지 접근 권한)
    private fun verifyPermission() {
        val permission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ),
                1
            )
        }
    }
}