package com.project.concon.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentWinnerSelectBinding
import com.project.concon.model.remote.dto.request.WinnerRequest
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.recyclerview.adapter.RecyclerViewWinnerSelectAdapter
import com.project.concon.widget.recyclerview.decoration.RecyclerViewDecoration
import com.project.concon.viewmodel.WinnerSelectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WinnerSelectFragment : BaseFragment<FragmentWinnerSelectBinding, WinnerSelectViewModel>() {

    override val viewModel: WinnerSelectViewModel by viewModel()
    private val args : WinnerSelectFragmentArgs by navArgs()

    private val recyclerViewAdapter = RecyclerViewWinnerSelectAdapter()
    private val winnerPrizeList = mutableListOf<WinnerRequest>()
    private var rank = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        viewModel.getParticipantInfo(args.contestId)

        initRecyclerView()

        binding.winnerSelectButton.setOnClickListener {
            viewModel.putContestWinnerSelect(args.contestId, winnerPrizeList)
        }
        
        recyclerViewAdapter.setOnItemClickListener { view, participant ->
//            for (i in 0 until winnerPrizeList.size) {
//                if (winnerPrizeList[i].participantID == dataList.ID) {
//                    if (winnerPrizeList[i].participantID < dataList.ID) {
//                        winnerPrizeList[i].participantID++
//                    } else {
//                        winnerPrizeList[i].participantID--
//                    }
//                    winnerPrizeList.removeAt(i)
//                    return
//                } else {
                    view.setBackgroundColor(Color.parseColor("#C4C4C4"))
                    winnerPrizeList.add(WinnerRequest(rank, participant.ID))
                    rank++
//                }
//            }
        }
    }

    private fun observe() = with(viewModel) {
        isSuccessGetParticipantInfo.observe(viewLifecycleOwner) {
            recyclerViewAdapter.setData(it)
        }

        isSuccessPutContestWinnerSelect.observe(viewLifecycleOwner) {
            navController.navigate(WinnerSelectFragmentDirections.actionWinnerSelectFragmentToContestDetailFragment(args.contestId))
        }

        isFailure.observe(viewLifecycleOwner) {
            MessageUtils.showFailDialog(requireActivity(), it)
        }
    }

    private fun initRecyclerView() {
        val decoration = RecyclerViewDecoration(40)
        binding.winnerSelectRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
    }

    override fun init() {
    }

    override fun observerViewModel() {
    }
}