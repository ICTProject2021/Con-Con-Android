package com.project.concon.view.fragment

import android.os.Bundle
import android.view.View
import com.project.concon.R
import com.project.concon.base.BaseVMFragment
import com.project.concon.databinding.FragmentProfileBinding
import com.project.concon.utils.MessageUtils
import com.project.concon.view.bind.setImage
import com.project.concon.viewmodel.ProfileViewModel
import java.text.NumberFormat
import java.util.*

class ProfileFragment : BaseVMFragment<FragmentProfileBinding, ProfileViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_profile

    override fun setBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        viewModel.getProfile()

        binding.charge.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_paymentFragment)
        }

        binding.withdraw.setOnClickListener {
            MessageUtils.showUpdateDialog(requireActivity(), "출금하기 기능은 추후 업데이트 예정입니다.")
        }

        binding.participationContest.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_participatedContestFragment)
        }
    }

    private fun observe() = with(viewModel) {
        isSuccess.observe(viewLifecycleOwner) {
            // 프로필 이미지가 없을 때 null 로 날아오기 때문에 null 값 체크
            // todo .....................
            if (it.profile != null) {
                binding.profileImg.setImage(it.profile)
            }

            val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
            val cash = numberFormat.format(it.cash)

            binding.cash.text = "${cash}원"
            binding.profileNickname.text = it.nickname
        }
    }
}