package com.project.concon.view.fragment

import androidx.navigation.fragment.navArgs
import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentContestDetailBinding
import com.project.concon.model.remote.dto.response.ContestDetail
import com.project.concon.viewmodel.ContestDetailViewModel
import com.project.concon.widget.extension.formatToString
import com.project.concon.widget.utils.MessageUtils
import java.util.*

/** 대회 상세 페이지 */
class ContestDetailFragment : BaseFragment<FragmentContestDetailBinding, ContestDetailViewModel>() {
    private lateinit var now: String
    private lateinit var contestDetail: ContestDetail

    private val args by navArgs<ContestDetailFragmentArgs>()

    override fun getViewModelClass(): Class<ContestDetailViewModel> =
        ContestDetailViewModel::class.java

    override fun init() {
        now = Calendar.getInstance().time.formatToString() // 현재 시각 구해서 문자열로 변
        viewModel.getContestDetail(args.id)
    }

    override fun observerViewModel() {
        with(viewModel) {
            onBack.observe(this@ContestDetailFragment) {
                navController.popBackStack()
            }

            onJoin.observe(this@ContestDetailFragment) {
                if (contestDetail.duedate < now) {
                    if (contestDetail.isHost)
                        navController.navigate(ContestDetailFragmentDirections.toWinnerFragment(args.id))
                    else
                        navController.navigate(ContestDetailFragmentDirections.toWinnerFragment(args.id))
                } else {
                    navController.navigate(ContestDetailFragmentDirections.toJoinContestFragment(args.id))
                }
            }

            isLoading.observe(this@ContestDetailFragment) {
                if (it) {
                    MessageUtils.showProgress(requireActivity())
                } else {
                    MessageUtils.dismissProgress()
                }
            }

            isSuccess.observe(this@ContestDetailFragment) {
                contestDetail = it

                if (it.duedate < now) {
                    if (contestDetail.isHost) {
                        binding.btnJoinContestDetail.text = "우승자 선택하기"
                    } else {
                        binding.btnJoinContestDetail.text = "대회 결과 조회하기"
                    }
                }
            }

            isFailure.observe(this@ContestDetailFragment) {
                MessageUtils.showFailDialog(requireActivity(), getString(R.string.error_server))
            }
        }
    }
}