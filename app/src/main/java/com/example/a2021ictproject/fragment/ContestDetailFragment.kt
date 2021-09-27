package com.example.a2021ictproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.ContestDetailFragmentBinding
import com.example.a2021ictproject.network.dto.response.ContestDetail
import com.example.a2021ictproject.viewmodel.ContestDetailViewModel

class ContestDetailFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private lateinit var binding: ContestDetailFragmentBinding
    private val viewModel: ContestDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.contest_detail_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id: Int? = arguments?.getInt("id")
        viewModel.getContestDetail(id!!)

        observe()

        binding.btnBackContestDetail.setOnClickListener {
            navigateToMain()
        }

        binding.btnJoinContestDetail.setOnClickListener {
            navigateToJoinContest(id)
        }
    }

    private fun observe() {
        viewModel.getContestDetailRes.observe(viewLifecycleOwner, {
            val data: ContestDetail = it!!.result
            val date = "시작 날짜 ~ ${viewModel.longToDateAsString(data.duedate)}"

            binding.tvTitleContestDetail.text = data.title
            binding.tvContentContestDetail.text = data.content
            binding.tvDateContestDetail.text = date
            binding.tvHostContestDetail.text = data.host
        })
    }

    private fun navigateToMain() {
        navController.navigate(R.id.action_contestDetailFragment_to_mainFragment)
    }

    private fun navigateToJoinContest(id: Int) {
        val bundle = Bundle()
        bundle.putInt("id", id)

        navController.navigate(R.id.action_contestDetailFragment_to_joinContestFragment, bundle)
    }
}