package com.project.concon.di.module

import androidx.lifecycle.ViewModel
import com.project.concon.model.repository.AccountRepository
import com.project.concon.viewmodel.SignInViewModel
import com.project.concon.viewmodel.factory.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {
    @Provides
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    fun providesSignInViewModel(repository: AccountRepository): ViewModel =
        SignInViewModel(repository)
}