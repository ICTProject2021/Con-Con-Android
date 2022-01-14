package com.project.concon.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.concon.viewmodel.factory.ViewModelFactory
import com.project.concon.viewmodel.factory.ViewModelKey
import com.project.concon.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindsSignInViewModel(signInViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindsSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContestDetailViewModel::class)
    abstract fun bindsContestDetailViewModel(contestDetailViewModel: ContestDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(JoinContestViewModel::class)
    abstract fun bindsJoinContestViewModel(joinContestViewModel: JoinContestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ParticipatedContestViewModel::class)
    abstract fun bindsParticipatedContestViewModel(participatedContestViewModel: ParticipatedContestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentViewModel::class)
    abstract fun bindsPaymentViewModel(paymentViewModel: PaymentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindsProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WinnerViewModel::class)
    abstract fun bindsWinnerViewModel(winnerViewModel: WinnerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WinnerSelectViewModel::class)
    abstract fun bindsWinnerSelectViewModel(winnerSelectViewModel: WinnerSelectViewModel): ViewModel
}