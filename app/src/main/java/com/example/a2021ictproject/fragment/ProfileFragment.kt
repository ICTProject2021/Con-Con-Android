package com.example.a2021ictproject.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.MainFragmentBinding
import com.example.a2021ictproject.databinding.ProfileFragmentBinding
import com.example.a2021ictproject.network.dto.response.Profile
import com.example.a2021ictproject.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.callApi()
        viewModel.getProfileLiveDataObserver().observe(viewLifecycleOwner, Observer<Profile> {
            Glide.with(binding.profileImg).load(it.profile).into(binding.profileImg)
            binding.cash.text = it.cash.toString()
            binding.profileNickname.text = it.nickname.toString()

        })
        // TODO: Use the ViewModel
    }

}