package com.project.concon.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import com.project.concon.data.SkuData

class BillingManager(private val listener: PurchaseMessageListener) :
    PurchasesUpdatedListener {

    private val TAG = "BillingManager"

    private lateinit var billingClient: BillingClient
    private var skuDetailList: List<SkuDetails> = listOf()
    private val consumerListener: ConsumeResponseListener = ConsumeResponseListener { result, _ ->
        if (result.responseCode == BillingClient.BillingResponseCode.OK) {

        }
    }

    fun build(context: Context) {
        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        Log.d(TAG, "BillingClient 설정 ok")

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    listener.test("구글 결제 서버 접속 성공")
                    Log.d(TAG, "구글 결제 서버 접속 성공")

                    getSkuDetailList()
                } else {
                    Log.d(TAG, "구글 결제 서버 접속 실패")
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.d(TAG, "결제 실패: 구글 결제 서버 접속 실패")

                listener.onPurchaseCancel()
            }
        })
    }

    private fun getSkuDetailList() {
        val skuList = arrayListOf(
            SkuData.MONEY_1000.sku,
            SkuData.MONEY_5000.sku,
            SkuData.MONEY_10000.sku,
            SkuData.MONEY_20000.sku,
            SkuData.MONEY_30000.sku
        )

        val params = SkuDetailsParams.newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.INAPP)

        billingClient.querySkuDetailsAsync(params.build()) { result, list ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK && list != null) {
                listener.test("인앱 상품 가져오기 성공: ${result.debugMessage}, $list")

                skuDetailList = list
            } else {
                listener.test("인앱 상품 가져오기 실패: ${result.debugMessage}, $list")
            }
        }
    }

    fun doBillingFlow(activity: Activity, sku: String) {
        var skuDetails: SkuDetails? = null

        skuDetailList.forEach {
            if (sku == it.sku)
                skuDetails = it
        }

        Log.d(TAG, "doBillingFlow: ${skuDetails.toString()}")

        if (skuDetails == null) {
            listener.onPurchaseCancel()
            return
        }

        val flow = BillingFlowParams.newBuilder().setSkuDetails(skuDetails!!).build()
        billingClient.launchBillingFlow(activity, flow)
    }

    // 결제 후 상품을 소비해주는 메서드
    private fun handlePurchase(purchase: Purchase) {
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.consumeAsync(consumeParams, consumerListener)
    }

    // 결제 성공 여부 리스너
    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            listener.test("결제 승인: $purchases")
            Log.d(TAG, "결제 승인: $purchases")

            purchases.forEach {
                handlePurchase(it)
            }

            listener.onPurchaseAccept()
        } else if (result.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d(TAG, "결제 취소")

            listener.onPurchaseCancel()
        } else {
            Log.d(TAG, "결제 취소: ${result.responseCode}-${result.debugMessage}")

            listener.onPurchaseCancel()
        }
    }
}