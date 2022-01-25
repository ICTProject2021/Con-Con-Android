package com.project.concon.di.application

import android.app.Application
import com.project.concon.di.module.repositoryModule
import com.project.concon.di.module.serviceModule
import com.project.concon.di.module.viewModelModule
import com.project.concon.widget.utils.PreferenceUtils
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        PreferenceUtils.init(applicationContext)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(serviceModule, repositoryModule, viewModelModule))
        }
        super.onCreate()
    }
}