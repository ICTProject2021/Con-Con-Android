package com.example.a2021ictproject.fragment

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2021ictproject.Adapter.MainRecyclerViewAdapter
import com.example.a2021ictproject.R
import com.example.a2021ictproject.activity.MainActivity
import com.example.a2021ictproject.databinding.MainFragmentBinding
import com.example.a2021ictproject.network.dto.response.Contest
import com.example.a2021ictproject.viewmodel.MainViewModel

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

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).visibility()
    }

    private fun initRecyclerView() {
        binding.contestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = MainRecyclerViewAdapter()
            adapter = recyclerViewAdapter
        }
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.callApi()
        viewModel.getContestLiveDataObserver().observe(viewLifecycleOwner, {
            if (it != null) {
                recyclerViewAdapter.setData(it)
            } else {
                recyclerViewAdapter.setData(
                    listOf(
                        Contest(
                            id = 0,
                            dueDate = 0,
                            host = "",
                            profilepicture = "",
                            title = "아직 대회가 없어요!"
                        )
                    )
                )
            }
            recyclerViewAdapter.setOnItemClickListener(object: MainRecyclerViewAdapter.onItemClickListener{
                override fun onClick(v: View, position: Int) {
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToContestDetailFragment(it[position].id))
                    (activity as MainActivity).gone()
                }
            })
        })
    }
}