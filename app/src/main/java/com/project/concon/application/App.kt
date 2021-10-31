package com.project.concon.application

import android.app.Application
import com.project.concon.utils.PreferenceUtils

class App : Application() {
    override fun onCreate() {
        PreferenceUtils.init(applicationContext)
        super.onCreate()
    }
}