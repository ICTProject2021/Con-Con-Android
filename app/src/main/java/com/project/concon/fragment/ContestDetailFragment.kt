package com.project.concon.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.concon.R
import com.project.concon.databinding.ContestDetailFragmentBinding
import com.project.concon.network.dto.response.ContestDetail
import com.project.concon.viewmodel.ContestDetailViewModel
import java.time.LocalDate

class ContestDetailFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private lateinit var binding: ContestDetailFragmentBinding
    private val viewModel: ContestDetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    private val today: LocalDate = LocalDate.now()

    private val args by navArgs<ContestDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.contest_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        binding.data = ContestDetail(0, "", "", "", "", "", false, listOf())
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getContestDetail(args.id)

        observe()

        binding.btnBackContestDetail.setOnClickListener {
            navigateToMain()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observe() = with(viewModel) {
        getContestDetailRes.observe(viewLifecycleOwner) { data ->
            when(data) {
                null ->
                    Toast.makeText(requireContext(), getString(R.string.fail_server), Toast.LENGTH_SHORT).show()

                else -> {
                    binding.data = data
                    binding.btnJoinContestDetail.setOnClickListener {
                        if (binding.data?.duedate!! < today.toString()) {
                            if (data.isHost) navigateToWinnerSelect()
                            else navigateToWinner()
                        } else {
                            navigateToJoinContest()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToMain() {
        navController.navigate(R.id.action_contestDetailFragment_to_mainFragment)
    }

    private fun navigateToJoinContest() {
        navController.navigate(ContestDetailFragmentDirections.actionContestDetailFragmentToJoinContestFragment(args.id))
    }

    private fun navigateToWinner() {
        navController.navigate(ContestDetailFragmentDirections.actionContestDetailFragmentToWinnerFragment(args.id))
    }

    private fun navigateToWinnerSelect() {
        navController.navigate(ContestDetailFragmentDirections.actionContestDetailFragmentToWinnerSelectFragment(args.id))
    }
}