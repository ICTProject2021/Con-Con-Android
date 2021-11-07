package com.project.concon.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.project.concon.R
import com.project.concon.databinding.CreateContestFragmentBinding
import com.project.concon.network.dto.request.ContestRequest
import com.project.concon.network.dto.response.Prize
import com.project.concon.utils.MessageUtils
import com.project.concon.viewmodel.CreateContestViewModel
import com.project.concon.viewmodel.DialogViewModel
import java.text.SimpleDateFormat

class CreateContestFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private lateinit var binding: CreateContestFragmentBinding
    private val viewModel: CreateContestViewModel by activityViewModels()
    private val pViewModel: DialogViewModel by activityViewModels()

    private var startTime: Long = 0
    private var dueTime: Long = 0
    private var prizeList: MutableList<Prize> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_contest_fragment, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        binding.etDateCreateContest.setOnClickListener { showCalendar() }

        /* focus 일 때, 마지막에 원을 떼고, focus가 아닐 때 원을 붙임 */

        binding.etPrizeCreateContest.apply {
//            setOnFocusChangeListener { _, hasFocus ->
//                if (hasFocus) {
//                    setText((text.toString()).replace("원", ""))
//                } else {
//                    if (!text.isNullOrBlank()) {
//
////                        NumberFormat.getInstance  (Locale.getDefault()).format(text.toString().trim())
//                        setText("${text}원")
//                    }
//                }
//            }
            setText("총 ${totalPrice().toString()} 원")

            setOnClickListener {
                navigateToPrize()
            }
        }

        binding.btnCloseCreateContest.setOnClickListener { navigateToMain() }

        binding.btnConfirmCreateContest.setOnClickListener {
            /* 에러 메세지 처리 */
            val errorMsg: String? = when {
                binding.etDateCreateContest.text.isNullOrBlank() -> "대회 날짜를"
                binding.etPrizeCreateContest.text.isNullOrBlank() -> "우승 상금을"
                binding.etTitleCreateContest.text.isNullOrBlank() -> "제목을"
                binding.etContentCreateContest.text.isNullOrBlank() -> "내용을"
                else -> null
            }

            if (errorMsg != null) {
                Toast.makeText(requireContext(), "$errorMsg 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("start", startTime.toString())
                Log.d("end", dueTime.toString())
                viewModel.postCreateContest(getCreateContest())
            }
        }
    }

    private fun observe() = with(viewModel) {
        postCreateContestRes.observe(viewLifecycleOwner) { it ->
            when (it) {
                null -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fail_server),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    Toast.makeText(requireContext(), "대회 등록에 성공.", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                }
            }
        }

        isLoading.observe(viewLifecycleOwner) {
            if (it) {
                MessageUtils.showProgress(requireActivity())
            } else {
                MessageUtils.dismissProgress()
            }
        }
    }

    private fun showCalendar() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("대회 기간 선택")
            .build()

        dateRangePicker.isCancelable = false
        dateRangePicker.addOnPositiveButtonClickListener {
//            startTime = it.first
//            dueTime = it.second
            viewModel.startTime.value = it.first
            viewModel.dueTime.value = it.second
            val text = "${viewModel.longTimeToDateAsString(it.first)} ~ ${viewModel.longTimeToDateAsString(it.second)}"
            viewModel.date.value = text
            binding.etDateCreateContest.setText(text)
        }

        dateRangePicker.show(requireActivity().supportFragmentManager, "Calendar")
    }

    private fun getCreateContest(): ContestRequest =
        ContestRequest(
            binding.etTitleCreateContest.text.toString(),
            binding.etContentCreateContest.text.toString(),
//            longTimeToDateAsString(startTime),
//            longTimeToDateAsString(dueTime),
            longTimeToDateAsString(viewModel.startTime.value),
            longTimeToDateAsString(viewModel.dueTime.value),
            pViewModel.prizeList.value!!
        )


    private fun navigateToMain() {
        navController.navigate(R.id.action_createContestFragment_to_mainFragment)
    }

    private fun navigateToPrize() {
        navController.navigate(R.id.action_createContestFragment_to_prizeFragment)
    }

    private fun longTimeToDateAsString(time: Long?): String {
//        val date: Date = Date(time)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss")
//        Log.d("longTimeToDateAsString", dateFormat.format(date))
        return dateFormat.format(time)
    }

    private fun totalPrice() : Int {
        var result: Int = 0
        pViewModel.prizeList.value?.forEach {
            result += it.price
        }
        return result
    }

}

