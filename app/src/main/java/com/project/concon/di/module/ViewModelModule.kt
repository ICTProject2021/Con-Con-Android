package com.project.concon.di.module

import androidx.lifecycle.ViewModel
import com.project.concon.viewmodel.SignInViewModel
import com.project.concon.viewmodel.SignUpViewModel
import com.project.concon.viewmodel.factory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindsSignInViewModel(signInViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindsSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel
}