package com.project.concon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.R
import com.project.concon.adapter.RecyclerViewWinnerAdapter
import com.project.concon.databinding.FragmentWinnerBinding
import com.project.concon.decoration.RecyclerViewDecoration
import com.project.concon.viewmodel.WinnerViewModel

class WinnerFragment : Fragment() {

    private val viewModel: WinnerViewModel by viewModels()
    private lateinit var binding: FragmentWinnerBinding
    private lateinit var recyclerViewAdapter: RecyclerViewWinnerAdapter
    private val args by navArgs<WinnerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWinnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        binding.btnCloseCreateContest.setOnClickListener {
            findNavController().navigate(WinnerFragmentDirections.actionWinnerFragmentToContestDetailFragment(args.contestId))
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
        viewModel.callWinnerList(args.contestId)
        viewModel.winnerLiveData.observe(viewLifecycleOwner, {
            if (it != null) {
                recyclerViewAdapter.setData(it)
                binding.winnerMsg.text = ""
            } else {
                recyclerViewAdapter.setData(listOf())
                binding.winnerMsg.text = "우승자가 아직 정해지지 않았어요!\n조금만 기다려주세요!"
            }
        })
    }

}