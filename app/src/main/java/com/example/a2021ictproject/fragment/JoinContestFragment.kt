package com.example.a2021ictproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.adapter.JoinContestRecyclerViewAdapter
import com.example.a2021ictproject.databinding.JoinContestFragmentBinding
import com.example.a2021ictproject.viewmodel.JoinContestViewModel

class JoinContestFragment : Fragment() {

    private val navController by lazy { findNavController() }

    private lateinit var binding: JoinContestFragmentBinding
    private val viewModel by lazy { JoinContestViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.join_contest_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        binding.btnBackJoinContest.setOnClickListener {
            navigateToContestDetail()
        }
    }

    private fun init() {
        val adapter = JoinContestRecyclerViewAdapter()
        binding.rvJoinContest.adapter = adapter

        val id: Int = arguments?.getInt("id")!!
        viewModel.getParticipantInfo(id)
    }

    private fun navigateToContestDetail() {
        navController.navigate(R.id.action_joinContestFragment_to_contestDetailFragment)
    }


}