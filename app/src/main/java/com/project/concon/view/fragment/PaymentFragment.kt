package com.project.concon.view.fragment

import android.os.Bundle
import android.view.View
import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentPaymentBinding
import com.project.concon.viewmodel.PaymentViewModel
import com.project.concon.widget.data.Payment
import com.project.concon.widget.data.SkuData
import com.project.concon.widget.recyclerview.adapter.RecyclerViewPaymentAdapter
import com.project.concon.widget.utils.BillingManager
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.utils.PurchaseListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentFragment : BaseFragment<FragmentPaymentBinding, PaymentViewModel>() {

//    override fun getViewModelClass(): Class<PaymentViewModel> = PaymentViewModel::class.java

    private val viewModel: PaymentViewModel by viewModel()
    private lateinit var manager: BillingManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClosePayment.setOnClickListener {
            navController.navigate(R.id.action_paymentFragment_to_profileFragment)
        }
    }

    override fun init() {
        initBilling()
        initRecyclerView()
    }

    private fun initBilling() {
        manager = BillingManager(object : PurchaseListener {
            override fun onPurchaseAccept(cash: Int) {
                viewModel.chargeCash(cash)
            }

            override fun onPurchaseCancel() {
                MessageUtils.showFailDialog(requireActivity(), "결제를 실패했어요.")
            }
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

    override fun observerViewModel() {
        with(viewModel) {
            onCloseEvent.observe(this@PaymentFragment) {
                navController.popBackStack()
            }
        }
    }
}