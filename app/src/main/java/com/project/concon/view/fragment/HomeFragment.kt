package com.project.concon.view.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentHomeBinding
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.recyclerview.adapter.RecyclerViewMainAdapter
import com.project.concon.widget.recyclerview.decoration.RecyclerViewDecoration
import com.project.concon.viewmodel.HomeViewModel
import com.project.concon.widget.extension.dismissProgress
import com.project.concon.widget.extension.showProgress
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val adapter = RecyclerViewMainAdapter()
    override val viewModel: HomeViewModel by viewModel()

    override fun init() {
        viewModel.getContestList()

        binding.contestRecyclerView.apply {
            adapter = this@HomeFragment.adapter
            addItemDecoration(RecyclerViewDecoration(40))
        }

        adapter.setOnItemClickListener {
            navController.navigate(HomeFragmentDirections.toContestDetailFragment(it))
        }
    }

    override fun observerViewModel() {
        with(viewModel) {
            isLoading.observe(viewLifecycleOwner) {
                if(it) showProgress() else dismissProgress()
            }

            isSuccess.observe(viewLifecycleOwner) {
                adapter.setData(it)
            }

            isFailure.observe(viewLifecycleOwner) {
                MessageUtils.showDefaultDialog(requireActivity(), it.toString())
            }
        }
    }
}