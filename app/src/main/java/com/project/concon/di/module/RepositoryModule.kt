package com.project.concon.di.module

import com.project.concon.model.remote.dao.AccountService
import com.project.concon.model.remote.dao.ContestService
import com.project.concon.model.repository.AccountRepository
import com.project.concon.model.repository.ContestRepository
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {
    @Provides
    fun providesAccountRepository(service: AccountService): AccountRepository =
        AccountRepository(service)

    @Provides
    fun providesContestRepository(service: ContestService): ContestRepository =
        ContestRepository(service)
}