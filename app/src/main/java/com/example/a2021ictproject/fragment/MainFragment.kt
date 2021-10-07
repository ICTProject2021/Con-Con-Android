package com.example.a2021ictproject.fragment

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2021ictproject.Adapter.MainRecyclerViewAdapter
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.MainFragmentBinding
import com.example.a2021ictproject.network.dto.response.Contest
import com.example.a2021ictproject.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        createData()
    }


    private fun initRecyclerView() {
        binding.contestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = MainRecyclerViewAdapter()
            adapter = recyclerViewAdapter
        }
    }

    private fun createData() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.callApi()
        viewModel.getContestLiveDataObserver().observe(viewLifecycleOwner, Observer<List<Contest>> {
            for (i in 0..it.size) {
                val list: ArrayList<Contest> = ArrayList<Contest>()
                list.add(it[i])
                recyclerViewAdapter.setData(list)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })

    }
}