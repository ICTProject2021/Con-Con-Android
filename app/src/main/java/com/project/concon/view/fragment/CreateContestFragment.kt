package com.project.concon.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.project.concon.R
import com.project.concon.databinding.FragmentCreateContestBinding
import com.project.concon.model.remote.dto.request.ContestRequest
import com.project.concon.model.remote.dto.response.Prize
import com.project.concon.widget.utils.ImagePicker
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.recyclerview.RecyclerViewJoinContestImageAdapter
import com.project.concon.viewmodel.CreateContestViewModel
import com.project.concon.viewmodel.PrizeDialogViewModel
import java.text.NumberFormat
import java.util.*

class CreateContestFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private lateinit var binding: FragmentCreateContestBinding
    private val viewModel: CreateContestViewModel by activityViewModels()
    private val pViewModelPrize: PrizeDialogViewModel by activityViewModels()

    private var startTime: Long = 0
    private var dueTime: Long = 0
    private var prizeList: MutableList<Prize> = mutableListOf()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val imageAdapter = RecyclerViewJoinContestImageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_contest, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observe()

        binding.etDateCreateContest.setOnClickListener { showCalendar() }

        /* focus 일 때, 마지막에 원을 떼고, focus가 아닐 때 원을 붙임 */

        binding.etPrizeCreateContest.apply {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
            val cash = numberFormat.format(getTotalPrice())

            setText("총 ${cash}원")

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
                MessageUtils.showFailDialog(requireActivity(), "$errorMsg 입력해주세요.")
            } else {
                Log.d("start", startTime.toString())
                Log.d("end", dueTime.toString())
                viewModel.postCreateContest(getCreateContest())
            }
        }

        binding.btnAddPhotoCreateContest.setOnClickListener {
            ImagePicker.multipleSelectStart(resultLauncher)
        }
    }

    private fun init() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            ImagePicker.init(it)
        }

        binding.rvPhotoCreateContest.adapter = imageAdapter
    }

    private fun observe() = with(viewModel) {
        isSuccess.observe(viewLifecycleOwner) { it ->
            when (it) {
                null -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_server),
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

        ImagePicker.imageList.observe(viewLifecycleOwner) {
            imageAdapter.setList(it)
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
            val text = "${viewModel.getDateAsString(it.first)} ~ ${viewModel.getDateAsString(it.second)}"
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
            viewModel.getDateAsString(viewModel.startTime.value),
            viewModel.getDateAsString(viewModel.dueTime.value),
            pViewModelPrize.prizeList.value!!
        )


    private fun navigateToMain() {
        navController.navigate(R.id.action_createContestFragment_to_mainFragment)
    }

    private fun navigateToPrize() {
        navController.navigate(R.id.action_createContestFragment_to_prizeFragment)
    }

    private fun getTotalPrice() : Int {
        var result = 0
        pViewModelPrize.prizeList.value?.forEach {
            result += it.price
        }
        return result
    }
}

