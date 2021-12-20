package com.project.concon.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.view.adapter.RecyclerViewWinnerSelectAdapter
import com.project.concon.databinding.FragmentWinnerSelectBinding
import com.project.concon.view.decoration.RecyclerViewDecoration
import com.project.concon.model.remote.dto.request.WinnerRequest
import com.project.concon.utils.MessageUtils
import com.project.concon.viewmodel.WinnerSelectViewModel

class WinnerSelectFragment : Fragment() {

    private lateinit var binding: FragmentWinnerSelectBinding
    private val viewModel: WinnerSelectViewModel by viewModels()
    private val args : WinnerSelectFragmentArgs by navArgs()
    private lateinit var recyclerViewAdapter: RecyclerViewWinnerSelectAdapter
    private val winnerPrizeList = mutableListOf<WinnerRequest>()
    private var rank = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWinnerSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("id", args.contestId.toString())

        initRecyclerView()
        initButton()
    }

    private fun initRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(40)
        binding.winnerSelectRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewWinnerSelectAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
        viewModel.callApi(args.contestId)
        viewModel.participantList.observe(viewLifecycleOwner, {
            if (it != null) {
                recyclerViewAdapter.setData(it)
            } else {
                recyclerViewAdapter.setData(listOf())
            }
            recyclerViewAdapter.setOnItemClickListener(object: RecyclerViewWinnerSelectAdapter.onItemClickListener{
                override fun onClick(v: View, position: Int) {
                    val dataList = recyclerViewAdapter.getData(position)
                    Log.d("dataList : ", dataList.toString())
//                    for (i in 0 until winnerPrizeList.size) {
//                        if (winnerPrizeList[i].participantID == dataList.ID) {
//                            if (winnerPrizeList[i].participantID < dataList.ID) {
//                                winnerPrizeList[i].participantID++
//                            } else {
//                                winnerPrizeList[i].participantID--
//                            }
//                            winnerPrizeList.removeAt(i)
//                            return
//                        } else {
                            v.setBackgroundColor(Color.parseColor("#C4C4C4"))
                            winnerPrizeList.add(WinnerRequest(rank, dataList.ID))
                            Log.d("dataList : ", dataList.ID.toString())
                            rank++
                            Log.d("WinnerPrizeList", winnerPrizeList.toString())
//                        }
//                    }
                }
            })
        })
    }

    private fun initButton() {
        binding.winnerSelectButton.setOnClickListener {
            viewModel.request(args.contestId, winnerPrizeList)
            Log.d("winnerPrizeList", winnerPrizeList.toString())
            viewModel.msg.observe(viewLifecycleOwner, {
                if (it != null) {
                    when (it.msg) {
                        "fail" -> MessageUtils.showFailDialog(requireActivity(), "등록에 실패했습니다.")

                        else -> {
                            findNavController().navigate(WinnerSelectFragmentDirections.actionWinnerSelectFragmentToContestDetailFragment(args.contestId))
                        }
                    }
                } else {
                    MessageUtils.showFailDialog(requireActivity(), "서버 통신에 실패했습니다.")
                }
            })
        }
    }

}