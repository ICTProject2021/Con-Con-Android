package com.project.concon.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.concon.R
import com.project.concon.databinding.FragmentChargeCashBinding
import com.project.concon.viewmodel.ChargeCashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/** 삭제 예정 */
class ChargeCashFragment : Fragment() {

    private val navController by lazy { findNavController() }

    private lateinit var binding: FragmentChargeCashBinding
    private val viewModel: ChargeCashViewModel by viewModel()

    private val buttons = mutableListOf<Button>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charge_cash, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons(view)

        observe()
    }

    private fun observe() = with(viewModel) {
        cashStack.observe(viewLifecycleOwner) {
            moneyFormat(it.lastElement())
        }

        putChargeRes.observe(viewLifecycleOwner) { it ->
            when (it?.msg) {
//                null -> MessageUtils.showDialog(requireContext(), "제목", resources.getString(R.string.error_server))

                else -> navigateToChargeCashFinish()
            }
        }

        binding.btnConfirm.setOnClickListener {
            when {
//                error.value != "" -> {
//                    putCharge()
//                }

                cash.value == "" ->
                    error.value = "이잉익잉 돈 입력해잉잉"

                cash.value!!.toInt() >= 1000000 ->
                    error.value = "이익 백 만원 이상은 안 됑!!!!!!!"

                else -> navController.navigate(R.id.action_chargeCashFragment_to_chargeCashFinishFragment)
            }
        }
    }

    private fun navigateToChargeCashFinish() {
        navController.navigate(R.id.action_chargeCashFragment_to_chargeCashFinishFragment)
    }

    // 버튼 클릭리스너 세팅
    private fun setButtons(v: View) {
        for (i in 0..9) {
            val id = "button${i}"
            buttons.add(v.findViewById(resources.getIdentifier(id, "id", requireActivity().packageName)))
        }

        buttons.forEach {
            it.setOnClickListener { v ->
                val btnName = v.resources.getResourceEntryName(v.id)
                val num = btnName.substring(btnName.length-1).toInt()
                viewModel.pushNumber(num)
            }
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.popNumber()
        }
    }
}