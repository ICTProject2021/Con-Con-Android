package com.project.concon.view.fragment

import android.view.View
import androidx.navigation.fragment.navArgs
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentWinnerBinding
import com.project.concon.viewmodel.WinnerViewModel
import com.project.concon.widget.recyclerview.adapter.RecyclerViewWinnerAdapter
import com.project.concon.widget.recyclerview.decoration.RecyclerViewDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class WinnerFragment : BaseFragment<FragmentWinnerBinding, WinnerViewModel>() {

    override val viewModel: WinnerViewModel by viewModel()

    private val adapter = RecyclerViewWinnerAdapter()
    private val args by navArgs<WinnerFragmentArgs>()

    override fun init() {
        initRecyclerView()
        viewModel.getWinnerList(args.contestId)

        binding.btnClose.setOnClickListener {
            navController.navigate(
                WinnerFragmentDirections.actionWinnerFragmentToContestDetailFragment(args.contestId)
            )
        }
    }

    override fun observerViewModel() {
        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner) {
                adapter.setData(it)

                if(it[0].nickname == null) {
                    binding.winnerMsg.text = "우승자가 아직 정해지지 않았어요!\n조금만 기다려주세요!"
                    binding.winnerList.visibility = View.GONE
                } else {
                    binding.winnerMsg.text = "대회 우승을 축하합니다!"
                    binding.winnerList.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initRecyclerView() {
        val decoration = RecyclerViewDecoration(40)
        binding.winnerList.apply {
            adapter = this@WinnerFragment.adapter
            addItemDecoration(decoration)
        }
    }
}
