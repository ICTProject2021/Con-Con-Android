package com.project.concon.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.project.concon.R
import com.project.concon.databinding.ProfileFragmentBinding
import com.project.concon.viewmodel.ProfileViewModel

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
                Glide.with(binding.profileImg).load(it.profile).into(binding.profileImg)
            }

            binding.cash.text = "${it.cash}원"
            binding.profileNickname.text = it.nickname
        })

        binding.withdraw.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_paymentFragment)
        }

        binding.participationContest.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_participatedContestFragment)
        }
    }
}