package com.project.concon.view.fragment

import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentContestDetailBinding
import com.project.concon.model.remote.dto.response.ContestDetail
import com.project.concon.viewmodel.ContestDetailViewModel
import com.project.concon.widget.extension.dismissProgress
import com.project.concon.widget.extension.formatToString
import com.project.concon.widget.extension.showProgress
import com.project.concon.widget.utils.MessageUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/** 대회 상세 페이지 */
class ContestDetailFragment : BaseFragment<FragmentContestDetailBinding, ContestDetailViewModel>() {
    override val viewModel: ContestDetailViewModel by viewModel()

    private lateinit var now: String
    private lateinit var contestDetail: ContestDetail

    private val args by navArgs<ContestDetailFragmentArgs>()

    override fun init() {
        now = Calendar.getInstance().time.formatToString() // 현재 시각 구해서 문자열로 변
        viewModel.getContestDetail(args.id)

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }

        binding.btnJoin.setOnClickListener {
            val directions: NavDirections = if (contestDetail.duedate < now) {
                if (contestDetail.isHost)
                    ContestDetailFragmentDirections.toWinnerSelectFragment(args.id)
                else
                    ContestDetailFragmentDirections.toWinnerFragment(args.id)
            } else {
                ContestDetailFragmentDirections.toJoinContestFragment(args.id)
            }

            navController.navigate(directions)
        }
    }

    override fun observerViewModel() {
        with(viewModel) {
            isLoading.observe(viewLifecycleOwner) {
                if(it) showProgress() else dismissProgress()
            }

            isSuccess.observe(viewLifecycleOwner) {
                contestDetail = it

                if (it.duedate < now) {
                    if (contestDetail.isHost) {
                        binding.btnJoin.text = "우승자 선택하기"
                    } else {
                        binding.btnJoin.text = "대회 결과 조회하기"
                    }
                }
            }

            isFailure.observe(viewLifecycleOwner) {
                MessageUtils.showFailDialog(requireActivity(), getString(R.string.error_server))
            }
        }
    }
}