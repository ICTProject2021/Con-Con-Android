package com.project.concon.di.component

import android.app.Application
import com.project.concon.di.application.App
import com.project.concon.di.module.RepositoryModule
import com.project.concon.di.module.ServiceModule
import com.project.concon.di.module.ViewModelFactoryModule
import com.project.concon.view.activity.IntroActivity
import com.project.concon.view.fragment.SignInFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    RepositoryModule::class,
    ServiceModule::class,
    ViewModelFactoryModule::class])
interface AppComponent : AndroidInjector<App> {

    fun inject(introActivity: IntroActivity)

    fun inject(signInFragment: SignInFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}