package com.project.concon.view.fragment

import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentProfileBinding
import com.project.concon.viewmodel.ProfileViewModel
import com.project.concon.widget.bind.setImage
import com.project.concon.widget.utils.MessageUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModel()

    override fun init() {
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

    override fun observerViewModel() {
        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner) {
                // 프로필 이미지가 없을 때 null 로 날아오기 때문에 null 값 체크
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
}