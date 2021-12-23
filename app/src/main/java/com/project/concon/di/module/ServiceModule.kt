package com.project.concon.di.module

import com.project.concon.model.remote.RetrofitInstance
import com.project.concon.model.remote.dao.AccountService
import com.project.concon.model.remote.dao.ContestService
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {
    @Provides
    fun providesAccountService(): AccountService =
        RetrofitInstance.accountService

    @Provides
    fun providesContestService(): ContestService =
        RetrofitInstance.contestService
}