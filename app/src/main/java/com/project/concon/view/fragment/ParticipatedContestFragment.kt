package com.project.concon.view.fragment

import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentParticipatedContestBinding
import com.project.concon.viewmodel.ParticipatedContestViewModel
import com.project.concon.widget.recyclerview.adapter.RecyclerViewParticipatedAdapter
import com.project.concon.widget.recyclerview.decoration.RecyclerViewDecoration

class ParticipatedContestFragment
    : BaseFragment<FragmentParticipatedContestBinding, ParticipatedContestViewModel>() {

    override fun getViewModelClass(): Class<ParticipatedContestViewModel> = ParticipatedContestViewModel::class.java

    private val adapter = RecyclerViewParticipatedAdapter()

    override fun init() {
        viewModel.getMyParticipatedContestList()

        val decoration = RecyclerViewDecoration(40)
        binding.contestRecyclerView.apply {
            adapter = this@ParticipatedContestFragment.adapter
            addItemDecoration(decoration)
        }

        adapter.setOnItemClickListener {
            navController.navigate(ParticipatedContestFragmentDirections.toContestDetailFragment(it))
        }
    }

    override fun observerViewModel() {
        with(viewModel) {
            onBackEvent.observe(this@ParticipatedContestFragment) {
                navController.popBackStack()
            }

            isSuccess.observe(this@ParticipatedContestFragment) {
                adapter.setList(it)
            }

            isFailure.observe(this@ParticipatedContestFragment) {
            }
        }
    }
}