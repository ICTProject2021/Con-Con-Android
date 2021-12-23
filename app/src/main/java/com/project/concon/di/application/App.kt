package com.project.concon.di.application

import com.project.concon.di.component.AppComponent
import com.project.concon.di.component.DaggerAppComponent
import com.project.concon.utils.PreferenceUtils
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {
    lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(this)
        PreferenceUtils.init(applicationContext)
        super.onCreate()
    }
}