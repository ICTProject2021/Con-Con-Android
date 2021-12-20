package com.project.concon.di.application

import android.app.Application
import com.project.concon.utils.PreferenceUtils

class DaggerApplication : Application() {
    override fun onCreate() {
        PreferenceUtils.init(applicationContext)
        super.onCreate()
    }
}