package com.project.concon.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillingManager(private val listener: PurchaseMessageListener) :
    PurchasesUpdatedListener {

    private val TAG = "BillingManager"

    private lateinit var billingClient: BillingClient
    private var skuDetailList: List<SkuDetails> = listOf()
    private lateinit var consumerListener: ConsumeResponseListener

    fun init(context: Context, item: String, activity: Activity) {
        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "구글 결제 서버 접속 성공")
                    getSkuDetailList()

                    CoroutineScope(Dispatchers.IO).launch {
                        val resultItem = billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP)
                        val listItem = resultItem.purchasesList

                        listItem.forEach {
                            if (it.purchaseState == Purchase.PurchaseState.PURCHASED) {
                                handlePurchase(it)
                            }
                        }

                        purchase(item, activity)
                    }
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
            "test1",
            "test2",
            "test3",
            "test4",
            "test5",
            "test6",
        )

        val params = SkuDetailsParams.newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.INAPP)

        billingClient.querySkuDetailsAsync(params.build()) { result, skuDetailsList ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                Log.d(TAG, "인앱 상품 가져오기 성공")
                this.skuDetailList = skuDetailsList

                // 대충 화면에 띄우는.. 로직..
                this.skuDetailList.forEach {
                    Log.d(TAG, "${it.sku} : ${it.title}, ${it.price}")
                    Log.d(TAG, it.originalJson)
                }
            } else {
                Log.d(TAG, "인앱 상품 가져오기 실패: ${result.responseCode}-${result.debugMessage}")
            }
        }
    }

    fun purchase(item: String, activity: Activity) {
        var skuDetails: SkuDetails? = null

        skuDetailList.forEach {
            if (it.sku == item) {
                skuDetails = it
                return@forEach
            }
        }

        if (skuDetails != null) {
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails!!)
                .build()

            billingClient.launchBillingFlow(activity, flowParams)
        }
    }

    // 결제 후 상품을 소비해주는 메서드
    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

            billingClient.consumeAsync(consumeParams, consumerListener)
        }
    }

    // 결제 성공 여부 리스너
    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
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