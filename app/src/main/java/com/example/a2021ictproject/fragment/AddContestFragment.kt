package com.example.a2021ictproject.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.AddContestFragmentBinding
import com.example.a2021ictproject.viewmodel.AddContestViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.internal.TextWatcherAdapter

class AddContestFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private lateinit var binding: AddContestFragmentBinding
    private val viewModel: AddContestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_contest_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDateAddContest.setOnClickListener { showCalendar() }

        /* focus 일 때, 마지막에 원을 떼고, focus가 아닐 때 원을 붙임 */
        binding.etCashAddContest.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setText((text.toString()).replace("원", ""))
                } else {
                    if (!text.isNullOrBlank())
                        setText("${text}원")
                }
            }
        }

        binding.btnCloseAddContest.setOnClickListener { navigateToMain() }

        binding.btnConfirmAddContest.setOnClickListener {
            // Todo (서버 통신)
            navigateToMain()
        }
    }

    private fun showCalendar() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("")
            .build()

        dateRangePicker.isCancelable = false
        dateRangePicker.addOnPositiveButtonClickListener {
            val text = "${viewModel.longTimeToDateAsString(it.first)} ~ ${viewModel.longTimeToDateAsString(it.second)}"
            binding.etDateAddContest.setText(text)
        }

        dateRangePicker.show(requireActivity().supportFragmentManager, "Calendar")
    }

    private fun navigateToMain() {
        navController.navigate(R.id.action_addContestFragment_to_mainFragment)
    }
}