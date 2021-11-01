package com.project.concon.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.adapter.MainRecyclerViewAdapter
import com.project.concon.databinding.MainFragmentBinding
import com.project.concon.decoration.RecyclerViewDecoration
import com.project.concon.viewmodel.MainViewModel

class MainFragment : Fragment() {


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var recyclerViewAdapter: MainRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

//    override fun onResume() {
//        super.onResume()
//        (activity as MainActivity).visibility()
//    }

    private fun initRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(40)
        binding.contestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = MainRecyclerViewAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.callApi()
        viewModel.getContestLiveDataObserver().observe(viewLifecycleOwner, {
            if (it != null) {
                recyclerViewAdapter.setData(it)
            } else {
                recyclerViewAdapter.setData(
                    listOf()
//                    listOf(
//                        Contest(
//                            ID = 0,
//                            dueDate = 0,
//                            host = "",
//                            profile = "",
//                            title = "아직 대회가 없어요!"
//                        )
//                    )
                )
            }
            recyclerViewAdapter.setOnItemClickListener(object: MainRecyclerViewAdapter.onItemClickListener{
                override fun onClick(v: View, position: Int) {
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToContestDetailFragment(
                        it[position].ID!!
                    ))
//                    (activity as MainActivity).gone()
                }
            })
        })
    }
}