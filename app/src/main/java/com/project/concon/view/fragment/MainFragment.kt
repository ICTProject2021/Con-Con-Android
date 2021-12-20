package com.project.concon.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.view.adapter.RecyclerViewMainAdapter
import com.project.concon.databinding.MainFragmentBinding
import com.project.concon.view.decoration.RecyclerViewDecoration
import com.project.concon.utils.MessageUtils
import com.project.concon.viewmodel.HomeViewModel

class MainFragment : Fragment() {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var recyclerViewAdapter: RecyclerViewMainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getContestList()

        initRecyclerView()
        observe()
    }

    private fun initRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(40)
        binding.contestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewMainAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }

        recyclerViewAdapter.setOnItemClickListener {
            navController.navigate(MainFragmentDirections.actionMainFragmentToContestDetailFragment(it))
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

        getContestLiveDataObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerViewAdapter.setData(it)
            } else {
                recyclerViewAdapter.setData(
                    listOf()
                )
            }
        }
    }
}