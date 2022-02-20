package com.project.concon.view.fragment

import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentParticipatedContestBinding
import com.project.concon.viewmodel.ParticipatedContestViewModel
import com.project.concon.widget.recyclerview.adapter.RecyclerViewParticipatedAdapter
import com.project.concon.widget.recyclerview.decoration.RecyclerViewDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParticipatedContestFragment
    : BaseFragment<FragmentParticipatedContestBinding, ParticipatedContestViewModel>() {

    override val viewModel: ParticipatedContestViewModel by viewModel()

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
            onBackEvent.observe(viewLifecycleOwner) {
                navController.popBackStack()
            }

            isSuccess.observe(viewLifecycleOwner) {
                adapter.setList(it)
            }

            isFailure.observe(viewLifecycleOwner) {
            }
        }
    }
}