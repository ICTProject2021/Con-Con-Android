package com.project.concon.di.module

import com.project.concon.model.remote.RetrofitInstance
import com.project.concon.model.repository.AccountRepository
import com.project.concon.model.repository.ContestRepository
import com.project.concon.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val serviceModule = module {
    single {
        RetrofitInstance.accountService
    }
    single {
        RetrofitInstance.contestService
    }
}

val repositoryModule = module {
    single {
        AccountRepository(get())
    }
    single {
        ContestRepository(get())
    }
}

val viewModelModule = module {
    viewModel { ContestDetailViewModel(get()) }
    viewModel { WinnerViewModel(get()) }
    viewModel { WinnerSelectViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { PaymentViewModel(get()) }
    viewModel { ParticipatedContestViewModel(get()) }
    viewModel { JoinContestViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { CreateContestViewModel(get()) }
    viewModel { PrizeViewModel() }
}