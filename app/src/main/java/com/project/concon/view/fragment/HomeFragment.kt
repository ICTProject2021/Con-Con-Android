package com.project.concon.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.R
import com.project.concon.base.BaseVMFragment
import com.project.concon.databinding.FragmentHomeBinding
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.recyclerview.RecyclerViewMainAdapter
import com.project.concon.widget.recyclerview.RecyclerViewDecoration
import com.project.concon.viewmodel.HomeViewModel

class HomeFragment : BaseVMFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun setBinding() {}

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    private lateinit var recyclerViewAdapter: RecyclerViewMainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getContestList()

        initRecyclerView()
        observe()
    }

    private fun initRecyclerView() {
        val decoration = RecyclerViewDecoration(40)
        binding.contestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewMainAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }

        recyclerViewAdapter.setOnItemClickListener {
            navController.navigate(HomeFragmentDirections.actionMainFragmentToContestDetailFragment(it))
        }
    }

    private fun observe() = with(viewModel) {
        isLoading.observe(viewLifecycleOwner) {
            if (it) {
                MessageUtils.showProgress(requireActivity())
            } else {
                MessageUtils.dismissProgress()
            }
        }

        isSuccess.observe(viewLifecycleOwner) {
            recyclerViewAdapter.setData(it)
        }

        isFailure.observe(viewLifecycleOwner) {
            MessageUtils.showDefaultDialog(requireActivity(), "fail!!!!!!!!!!!!")
        }
    }
}