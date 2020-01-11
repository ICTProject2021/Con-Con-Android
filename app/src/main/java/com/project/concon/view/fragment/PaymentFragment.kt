package com.project.concon.view.fragment

import android.os.Bundle
import android.view.View
import com.project.concon.R
import com.project.concon.view.base.BaseVMFragment
import com.project.concon.databinding.FragmentPaymentBinding
import com.project.concon.utils.BillingManager
import com.project.concon.utils.MessageUtils
import com.project.concon.utils.PurchaseMessageListener
import com.project.concon.view.adapter.RecyclerViewPaymentAdapter
import com.project.concon.view.data.Payment
import com.project.concon.view.data.SkuData
import com.project.concon.viewmodel.PaymentViewModel

class PaymentFragment : BaseVMFragment<FragmentPaymentBinding, PaymentViewModel>() {

    override fun setBinding() {}

    override fun getLayoutRes(): Int = R.layout.fragment_payment

    override fun getViewModelClass(): Class<PaymentViewModel> = PaymentViewModel::class.java

    private lateinit var manager: BillingManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBilling()
        initRecyclerView()

        binding.btnClosePayment.setOnClickListener {
            navController.navigate(R.id.action_paymentFragment_to_profileFragment)
        }
    }

    private fun initBilling() {
        manager = BillingManager(object : PurchaseMessageListener {
            override fun onPurchaseAccept(cash: Int) {
                viewModel.putCharge(cash)
            }

            override fun onPurchaseCancel() {
                MessageUtils.showFailDialog(requireActivity(), "결제를 실패했어요.")
            }

            override fun test(msg: String) {}
        })

        manager.build(requireContext())
    }

    private fun initRecyclerView() {
        val list = listOf(
            Payment(SkuData.MONEY_1000.money, SkuData.MONEY_1000.skuName),
            Payment(SkuData.MONEY_5000.money, SkuData.MONEY_5000.skuName),
            Payment(SkuData.MONEY_10000.money, SkuData.MONEY_10000.skuName),
            Payment(SkuData.MONEY_20000.money, SkuData.MONEY_20000.skuName),
            Payment(SkuData.MONEY_30000.money, SkuData.MONEY_30000.skuName)
        )
        val adapter = RecyclerViewPaymentAdapter(list)

        binding.rvPayment.adapter = adapter

        adapter.setOnClickPaymentListener {
            val sku = when (it) {
                SkuData.MONEY_1000.position -> SkuData.MONEY_1000.sku
                SkuData.MONEY_5000.position -> SkuData.MONEY_5000.sku
                SkuData.MONEY_10000.position -> SkuData.MONEY_10000.sku
                SkuData.MONEY_20000.position -> SkuData.MONEY_20000.sku
                SkuData.MONEY_30000.position -> SkuData.MONEY_30000.sku
                else -> ""
            }

            manager.doBillingFlow(requireActivity(), sku)
        }
    }
}