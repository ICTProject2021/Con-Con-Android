package com.example.a2021ictproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2021ictproject.R
import com.example.a2021ictproject.adapter.ParticipatedRecyclerViewAdapter
import com.example.a2021ictproject.databinding.FragmentParticipatedContestBinding
import com.example.a2021ictproject.decoration.RecyclerViewDecoration
import com.example.a2021ictproject.viewmodel.ParticipatedContestViewModel

class ParticipatedContestFragment : Fragment() {

    private val viewModel: ParticipatedContestViewModel by viewModels()
    private lateinit var binding: FragmentParticipatedContestBinding
    private lateinit var recyclerViewAdapter: ParticipatedRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParticipatedContestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackParticipatedContest.setOnClickListener {
            findNavController().navigate(ParticipatedContestFragmentDirections.actionParticipatedContestFragmentToProfileFragment())
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val decoration = RecyclerViewDecoration(40)
        binding.contestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = ParticipatedRecyclerViewAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
        viewModel.callApi()
        viewModel.getObserver().observe(viewLifecycleOwner, {
            if  (it != null) {
                recyclerViewAdapter.setData(it)
            } else {
                recyclerViewAdapter.setData(listOf())
            }
            recyclerViewAdapter.setOnItemClickListener(object: ParticipatedRecyclerViewAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    findNavController().navigate(ParticipatedContestFragmentDirections.actionParticipatedContestFragmentToContestDetailFragment(it[position].ID!!))
                }
            })
        })
    }

}