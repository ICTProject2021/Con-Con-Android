package com.example.a2021ictproject.application

import android.app.Application
import com.example.a2021ictproject.data.Pref

class App : Application() {
    companion object {
        lateinit var pref: Pref
    }

    override fun onCreate() {
        pref = Pref(applicationContext)
        super.onCreate()
    }
}