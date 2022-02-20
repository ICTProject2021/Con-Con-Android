package com.project.concon.model.repository

import com.project.concon.model.remote.BaseRemote
import com.project.concon.model.remote.dao.AccountService
import com.project.concon.model.remote.dto.request.CashRequest
import com.project.concon.model.remote.dto.request.IdRequest
import com.project.concon.model.remote.dto.request.SignInRequest
import com.project.concon.model.remote.dto.request.SignUpRequest
import com.project.concon.model.remote.dto.response.Profile
import io.reactivex.rxjava3.core.Single

class AccountRepository(override val service: AccountService) : BaseRemote<AccountService>() {

    fun postSignIn(signInRequest: SignInRequest): Single<String> =
        service.postSignIn(signInRequest).map(getResponse())

    fun postSignUp(signUpRequest: SignUpRequest): Single<String> =
        service.postSignUp(signUpRequest).map(getResponse())

    fun postCheckId(idRequest: IdRequest): Single<String> =
        service.postCheckId(idRequest).map(getMessage())

    fun getProfile(): Single<Profile> =
        service.getProfile().map(getResponse())

    fun putChargeCash(cashRequest: CashRequest): Single<String> =
        service.putChargeCash(cashRequest).map(getMessage())
}