package com.project.concon.view.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.project.concon.R
import com.project.concon.base.BaseVMFragment
import com.project.concon.databinding.FragmentContestDetailBinding
import com.project.concon.model.remote.dto.response.ContestDetail
import com.project.concon.utils.MessageUtils
import com.project.concon.viewmodel.ContestDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class ContestDetailFragment : BaseVMFragment<FragmentContestDetailBinding, ContestDetailViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_contest_detail

    override fun setBinding() {
        binding.vm = viewModel
        binding.data = ContestDetail(0, "", "", "", "", "", "", false, listOf())
    }

    private lateinit var now: String
    private lateinit var contestDetail: ContestDetail

    private val args by navArgs<ContestDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        val time = Calendar.getInstance().time
        now = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(time)

        viewModel.getContestDetail(args.id)

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

        isSuccess.observe(viewLifecycleOwner) {
            contestDetail = it
            binding.data = it

            if (it.duedate < now) {
                if (contestDetail.isHost) {
                    binding.btnJoinContestDetail.text = "우승자 선택하기"
                } else {
                    binding.btnJoinContestDetail.text = "대회 결과 조회하기"
                }
            }
        }

        isFailure.observe(viewLifecycleOwner) {
            MessageUtils.showFailDialog(requireActivity(), getString(R.string.error_server))
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