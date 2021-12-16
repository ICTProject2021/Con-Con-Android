package com.project.concon.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.concon.R
import com.project.concon.databinding.ContestDetailFragmentBinding
import com.project.concon.model.remote.dto.response.ContestDetail
import com.project.concon.utils.MessageUtils
import com.project.concon.viewmodel.ContestDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class ContestDetailFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private lateinit var binding: ContestDetailFragmentBinding
    private val viewModel: ContestDetailViewModel by viewModels()

    private lateinit var now: String
    private lateinit var contestDetail: ContestDetail

    private val args by navArgs<ContestDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.contest_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        binding.data = ContestDetail(0, "", "", "", "", "", "", false, listOf())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val time = Calendar.getInstance().time
        now = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(time)

        viewModel.getContestDetail(args.id)

        observe()

        binding.btnBackContestDetail.setOnClickListener {
            navigateToMain()
        }

        binding.btnJoinContestDetail.setOnClickListener {
            if (contestDetail.duedate < now) {
                if (contestDetail.isHost) {
                    navigateToWinnerSelect()
                }
                else navigateToWinner()
            } else {
                navigateToJoinContest()
            }
        }
    }

    private fun observe() = with(viewModel) {
        isLoading.observe(viewLifecycleOwner) {
            if (it) {
                MessageUtils.showProgress(requireActivity())
            } else {
                MessageUtils.dismissProgress()
            }
        }

        getContestDetailRes.observe(viewLifecycleOwner) { data ->
            when(data) {
                null ->
                    MessageUtils.showFailDialog(requireActivity(), getString(R.string.fail_server))

                else -> {
                    contestDetail = data
                    binding.data = data

                    Log.d("detail", "${binding.data?.duedate}, $now, ${binding.data?.duedate!! < now}")
                    if (data.duedate < now) {
                        if (contestDetail.isHost) {
                            binding.btnJoinContestDetail.text = "우승자 선택하기"
                        } else {
                            binding.btnJoinContestDetail.text = "대회 결과 조회하기"
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