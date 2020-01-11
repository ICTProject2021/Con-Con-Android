package com.project.concon.view.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.R
import com.project.concon.view.base.BaseVMFragment
import com.project.concon.databinding.FragmentParticipatedContestBinding
import com.project.concon.view.adapter.RecyclerViewParticipatedAdapter
import com.project.concon.view.decoration.RecyclerViewDecoration
import com.project.concon.viewmodel.ParticipatedContestViewModel

class ParticipatedContestFragment
    : BaseVMFragment<FragmentParticipatedContestBinding, ParticipatedContestViewModel>() {

    override fun setBinding() {}

    override fun getLayoutRes(): Int = R.layout.fragment_participated_contest

    override fun getViewModelClass(): Class<ParticipatedContestViewModel> =
        ParticipatedContestViewModel::class.java

    private lateinit var recyclerViewParticipatedAdapter: RecyclerViewParticipatedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        initRecyclerView()

        viewModel.getMyContest()

        binding.btnBackParticipatedContest.setOnClickListener {
            findNavController().navigate(ParticipatedContestFragmentDirections.actionParticipatedContestFragmentToProfileFragment())
        }
    }

    private fun initRecyclerView() {
        val decoration = RecyclerViewDecoration(40)
        binding.contestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewParticipatedAdapter = RecyclerViewParticipatedAdapter()
            adapter = recyclerViewParticipatedAdapter
            addItemDecoration(decoration)
        }
    }

    private fun observe() = with(viewModel) {
        isSuccess.observe(viewLifecycleOwner) {
            recyclerViewParticipatedAdapter.setList(it)
            recyclerViewParticipatedAdapter.setOnItemClickListener(
                object: RecyclerViewParticipatedAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int) {
                        navController.navigate(
                            ParticipatedContestFragmentDirections.actionParticipatedContestFragmentToContestDetailFragment(
                                it[position].ID!!
                            ))
                    }
                })
        }
    }
}