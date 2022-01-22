package com.project.concon.view.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentHomeBinding
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.recyclerview.adapter.RecyclerViewMainAdapter
import com.project.concon.widget.recyclerview.decoration.RecyclerViewDecoration
import com.project.concon.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val recyclerViewAdapter = RecyclerViewMainAdapter()

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun init() {
        viewModel.getContestList()

        val decoration = RecyclerViewDecoration(40)

        binding.contestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }

        // TODO(recyclerView item click event 는 어떻게 처리해야하는지 확인하)
        recyclerViewAdapter.setOnItemClickListener {
            navController.navigate(HomeFragmentDirections.toContestDetailFragment(it))
        }
    }

    override fun observerViewModel() {
        with(viewModel) {
            isLoading.observe(this@HomeFragment) {
                if (it) {
                    MessageUtils.showProgress(requireActivity())
                } else {
                    MessageUtils.dismissProgress()
                }
            }

            isSuccess.observe(this@HomeFragment) {
                recyclerViewAdapter.setData(it)
            }

            isFailure.observe(this@HomeFragment) {
                MessageUtils.showDefaultDialog(requireActivity(), it.toString())
            }
        }
    }
}