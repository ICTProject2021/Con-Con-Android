package com.example.a2021ictproject.application

import android.app.Application
import com.example.a2021ictproject.utils.PreferenceUtils

class App : Application() {
    override fun onCreate() {
        PreferenceUtils.init(applicationContext)
        super.onCreate()
    }
}