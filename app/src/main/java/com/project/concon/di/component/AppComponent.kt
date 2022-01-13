package com.project.concon.di.component

import android.app.Application
import com.project.concon.di.application.App
import com.project.concon.di.module.*
import com.project.concon.view.fragment.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    RepositoryModule::class,
    ServiceModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<App> {

    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(contestDetailFragment: ContestDetailFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(joinContestFragment: JoinContestFragment)
    fun inject(participatedContestFragment: ParticipatedContestFragment)
    fun inject(paymentFragment: PaymentFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(winnerFragment: WinnerFragment)
    fun inject(winnerSelectFragment: WinnerSelectFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}