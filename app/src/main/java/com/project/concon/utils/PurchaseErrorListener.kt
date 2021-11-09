package com.project.concon.utils

interface PurchaseMessageListener {

    // 유저가 결제를 승인했을 때 메서드
    fun onPurchaseAccept(cash: Int)

    // 결제가 취소되었을 때 메서드
    fun onPurchaseCancel()

    fun test(msg: String)
}