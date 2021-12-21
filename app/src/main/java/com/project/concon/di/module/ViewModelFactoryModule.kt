package com.project.concon.di.module

import androidx.lifecycle.ViewModelProvider
import com.project.concon.viewmodel.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}