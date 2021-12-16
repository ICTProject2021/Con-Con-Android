package com.project.concon.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.project.concon.R
import com.project.concon.databinding.ProfileFragmentBinding
import com.project.concon.utils.MessageUtils
import com.project.concon.view.bind.setImage
import com.project.concon.viewmodel.ProfileViewModel
import java.text.NumberFormat
import java.util.*

class ProfileFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        viewModel.callApi()

        viewModel.getProfileLiveDataObserver().observe(viewLifecycleOwner, {
            // 프로필 이미지가 없을 때 null로 날아오기 때문에 null 값 체크
            if (it.profile != null) {
                binding.profileImg.setImage(it.profile)
            }

            val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
            val cash = numberFormat.format(it.cash)

            binding.cash.text = "${cash}원"
            binding.profileNickname.text = it.nickname
        })

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
}