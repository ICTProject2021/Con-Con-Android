package com.project.concon.di.module

import com.project.concon.view.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun signInFragment(): SignInFragment

    @ContributesAndroidInjector
    abstract fun signUpFragment(): SignUpFragment

    @ContributesAndroidInjector
    abstract fun contestDetailFragment(): ContestDetailFragment

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun joinContestFragment(): JoinContestFragment

    @ContributesAndroidInjector
    abstract fun participatedContestFragment(): ParticipatedContestFragment

    @ContributesAndroidInjector
    abstract fun paymentFragment(): PaymentFragment

    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun winnerFragment(): WinnerFragment

    @ContributesAndroidInjector
    abstract fun winnerSelectFragment(): WinnerSelectFragment
}