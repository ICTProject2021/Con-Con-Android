package com.project.concon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.PaymentFragmentBinding
import com.project.concon.utils.BillingManager
import com.project.concon.utils.MessageUtils
import com.project.concon.utils.PurchaseMessageListener

class PaymentFragment : Fragment() {

    private lateinit var binding: PaymentFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.payment_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTest1.setOnClickListener {
            val manager = BillingManager(object : PurchaseMessageListener {
                override fun onPurchaseAccept() {
                    MessageUtils.showDialog(requireContext(), "알림", "결제 성공!")
                }

                override fun onPurchaseCancel() {
                    MessageUtils.showDialog(requireContext(), "알림", "결제 실패! ㅠㅠ")
                }
            })

            manager.init(requireContext(), "test1", requireActivity())
        }

    }


}