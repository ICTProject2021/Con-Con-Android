package com.project.concon.di.component

import android.app.Application
import com.project.concon.di.application.App
import com.project.concon.di.module.*
import com.project.concon.view.fragment.SignInFragment
import com.project.concon.view.fragment.SignUpFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    RepositoryModule::class,
    ServiceModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<App> {

    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}