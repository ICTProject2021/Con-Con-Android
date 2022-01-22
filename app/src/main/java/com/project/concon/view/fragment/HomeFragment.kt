package com.project.concon.view.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentHomeBinding
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.recyclerview.adapter.RecyclerViewMainAdapter
import com.project.concon.widget.recyclerview.decoration.RecyclerViewDecoration
import com.project.concon.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val recyclerViewAdapter = RecyclerViewMainAdapter()
    override val viewModel: HomeViewModel by viewModel()

    override fun init() {
        viewModel.getContestList()

        val decoration = RecyclerViewDecoration(40)

        binding.contestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }

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