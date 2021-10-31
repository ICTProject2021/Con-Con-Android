package com.project.concon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.ChargeCashFinishFragmentBinding
import com.project.concon.viewmodel.ChargeCashViewModel

class ChargeCashFinishFragment : Fragment() {

    private val viewModel: ChargeCashViewModel by activityViewModels()
    private lateinit var binding: ChargeCashFinishFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.charge_cash_finish_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }

    private fun observe() = with(viewModel) {
        putChargeRes.observe(viewLifecycleOwner) { it ->
            msg.value = when (it!!.msg) {
                resources.getString(R.string.server_success) ->
                    "르뽕뿡 캐시를 충전했어요!"

                resources.getString(R.string.server_fail) ->
                    "르뿡뿡 캐시를 충전하는데 실패했어요 ㅠㅠ."

                else ->
                    ""
            }
        }
    }
}