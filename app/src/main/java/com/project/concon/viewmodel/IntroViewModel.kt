package com.project.concon.viewmodel

import com.project.concon.base.BaseViewModel
import com.project.concon.widget.livedata.SingleLiveEvent

class IntroViewModel : BaseViewModel() {
    val onSignInEvent = SingleLiveEvent<Unit>()
    val onSignUpEvent = SingleLiveEvent<Unit>()

    fun signInEvent() {
        onSignInEvent.call()
    }

    fun signUpEvent() {
        onSignUpEvent.call()
    }
}