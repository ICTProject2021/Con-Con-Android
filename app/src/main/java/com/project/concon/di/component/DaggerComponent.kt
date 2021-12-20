package com.project.concon.di.component

import com.project.concon.di.module.RepositoryModule
import com.project.concon.di.module.ServiceModule
import com.project.concon.model.remote.dao.AccountService
import com.project.concon.model.remote.dao.ContestService
import com.project.concon.model.repository.AccountRepository
import com.project.concon.model.repository.ContestRepository
import dagger.Component

@Component(modules = [RepositoryModule::class, ServiceModule::class])
interface DaggerComponent {
    fun injectAccountRepository(): AccountRepository

    fun injectContestRepository(): ContestRepository

    fun injectAccountService(): AccountService

    fun injectContestService(): ContestService
}