package com.example.a2021ictproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.ContestDetailFragmentBinding
import com.example.a2021ictproject.network.dto.response.ContestDetail
import com.example.a2021ictproject.viewmodel.ContestDetailViewModel

class ContestDetailFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private lateinit var binding: ContestDetailFragmentBinding
    private val viewModel: ContestDetailViewModel by viewModels()

    private val args by navArgs<ContestDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.contest_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        binding.data = ContestDetail(0, "", "", "", "", "", listOf())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getContestDetail(args.id)

        observe()

        binding.btnBackContestDetail.setOnClickListener {
            navigateToMain()
        }

        binding.btnJoinContestDetail.setOnClickListener {
            navigateToJoinContest(args.id)
        }
    }

    private fun observe() = with(viewModel) {
        getContestDetailRes.observe(viewLifecycleOwner) {
            when(it) {
                null ->
                    Toast.makeText(requireContext(), getString(R.string.fail_server), Toast.LENGTH_SHORT).show()

                else -> {
                    binding.data = it
                }
            }
        }
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