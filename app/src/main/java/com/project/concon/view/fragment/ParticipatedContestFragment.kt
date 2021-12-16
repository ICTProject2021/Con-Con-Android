package com.project.concon.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.concon.view.adapter.RecyclerViewParticipatedAdapter
import com.project.concon.databinding.FragmentParticipatedContestBinding
import com.project.concon.view.decoration.RecyclerViewDecoration
import com.project.concon.viewmodel.ParticipatedContestViewModel

class ParticipatedContestFragment : Fragment() {

    private val viewModel: ParticipatedContestViewModel by viewModels()
    private lateinit var binding: FragmentParticipatedContestBinding
    private lateinit var recyclerViewParticipatedAdapter: RecyclerViewParticipatedAdapter

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
            recyclerViewParticipatedAdapter = RecyclerViewParticipatedAdapter()
            adapter = recyclerViewParticipatedAdapter
            addItemDecoration(decoration)
        }
        viewModel.callApi()
        viewModel.getObserver().observe(viewLifecycleOwner, {
            if  (it != null) {
                recyclerViewParticipatedAdapter.setData(it)
            } else {
                recyclerViewParticipatedAdapter.setData(listOf())
            }
            recyclerViewParticipatedAdapter.setOnItemClickListener(object: RecyclerViewParticipatedAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    findNavController().navigate(ParticipatedContestFragmentDirections.actionParticipatedContestFragmentToContestDetailFragment(it[position].ID!!))
                }
            })
        })
    }

}