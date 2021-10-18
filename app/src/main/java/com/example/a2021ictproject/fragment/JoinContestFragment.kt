package com.example.a2021ictproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a2021ictproject.R
import com.example.a2021ictproject.adapter.JoinContestRecyclerViewAdapter
import com.example.a2021ictproject.bind.submitList
import com.example.a2021ictproject.databinding.JoinContestFragmentBinding
import com.example.a2021ictproject.network.dto.response.Participant
import com.example.a2021ictproject.viewmodel.JoinContestViewModel

class JoinContestFragment : Fragment() {

    private val navController by lazy { findNavController() }

    private lateinit var binding: JoinContestFragmentBinding
    private val viewModel: JoinContestViewModel by viewModels()

    private val navArgs by navArgs<JoinContestFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.join_contest_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.participantList = ObservableArrayList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observe()

        binding.btnBackJoinContest.setOnClickListener {
            navigateToContestDetail()
        }
    }

    private fun observe() = with(viewModel) {
        getParticipantInfoRes.observe(viewLifecycleOwner) {
            binding.rvJoinContest.submitList(it!!)
        }

        postParticipantRes.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "대회 참가 성공", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        binding.rvJoinContest.adapter = JoinContestRecyclerViewAdapter()
        viewModel.getParticipantInfo(navArgs.id)
    }

    private fun navigateToContestDetail() {
        navController.navigate(JoinContestFragmentDirections.actionJoinContestFragmentToContestDetailFragment(navArgs.id))
    }


}