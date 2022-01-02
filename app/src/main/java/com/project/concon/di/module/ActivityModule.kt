package com.project.concon.di.module

import com.project.concon.view.fragment.SignInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeSignInFragment(): SignInFragment
}