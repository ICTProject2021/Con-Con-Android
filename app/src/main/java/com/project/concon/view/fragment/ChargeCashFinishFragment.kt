package com.project.concon.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.project.concon.R
import com.project.concon.databinding.FragmentChargeCashFinishBinding
import com.project.concon.view.viewmodel.ChargeCashViewModel

/** 메이비 삭제 예정 */
class ChargeCashFinishFragment : Fragment() {

    private val viewModel: ChargeCashViewModel by activityViewModels()
    private lateinit var binding: FragmentChargeCashFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charge_cash_finish, container, false)
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