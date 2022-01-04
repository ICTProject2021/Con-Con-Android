package com.project.concon.view.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.R
import com.project.concon.base.BaseVMFragment
import com.project.concon.databinding.FragmentWinnerBinding
import com.project.concon.view.adapter.RecyclerViewWinnerAdapter
import com.project.concon.view.decoration.RecyclerViewDecoration
import com.project.concon.viewmodel.WinnerViewModel

class WinnerFragment : BaseVMFragment<FragmentWinnerBinding, WinnerViewModel>() {

    override fun setBinding() {}

    override fun getLayoutRes(): Int = R.layout.fragment_winner

    override fun getViewModelClass(): Class<WinnerViewModel> = WinnerViewModel::class.java

    private lateinit var recyclerViewAdapter: RecyclerViewWinnerAdapter

    private val args by navArgs<WinnerFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        initRecyclerView()

        viewModel.getWinnerList(args.contestId)

        binding.btnCloseCreateContest.setOnClickListener {
            navController.navigate(
                WinnerFragmentDirections.actionWinnerFragmentToContestDetailFragment(args.contestId)
            )
        }
    }

    private fun initRecyclerView() {
        val decoration = RecyclerViewDecoration(40)
        binding.winnerList.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewWinnerAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
    }

    private fun observe() = with(viewModel) {
        isSuccess.observe(viewLifecycleOwner) {
            recyclerViewAdapter.setData(it)

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