package com.example.a2021ictproject.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.MainFragmentBinding
import com.example.a2021ictproject.databinding.ProfileFragmentBinding
import com.example.a2021ictproject.network.dto.response.Profile
import com.example.a2021ictproject.viewmodel.ProfileViewModel

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

            binding.cash.text = it.cash.toString() + "원"
            binding.profileNickname.text = it.nickname
        })

        binding.withdraw.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_chargeCashFragment)
        }

        binding.participationContest.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_participatedContestFragment)
        }
    }
}