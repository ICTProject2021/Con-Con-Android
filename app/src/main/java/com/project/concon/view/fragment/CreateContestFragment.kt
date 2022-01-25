package com.project.concon.view.fragment

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentCreateContestBinding
import com.project.concon.viewmodel.CreateContestViewModel
import com.project.concon.viewmodel.PrizeViewModel
import com.project.concon.widget.extension.DATE
import com.project.concon.widget.extension.dismissProgress
import com.project.concon.widget.extension.getDateAsString
import com.project.concon.widget.extension.showProgress
import com.project.concon.widget.recyclerview.adapter.RecyclerViewJoinContestImageAdapter
import com.project.concon.widget.utils.ImagePicker
import com.project.concon.widget.utils.MessageUtils
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CreateContestFragment : BaseFragment<FragmentCreateContestBinding, CreateContestViewModel>() {

    companion object {
        private const val TAG = "CreateContestFragment"
    }

    override val viewModel: CreateContestViewModel by sharedViewModel()
    private val prizeViewModel: PrizeViewModel by sharedViewModel()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val imageAdapter = RecyclerViewJoinContestImageAdapter()

    override fun init() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            ImagePicker.init(it)
        }

        binding.rvPhotoCreateContest.adapter = imageAdapter

        binding.btnClose.setOnClickListener {
            navController.popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.apply {
                val errorMsg: String? = when {
                    date.value.isNullOrBlank() -> "대회 날짜를"
                    prize.value.isNullOrBlank() -> "우승 상금을"
                    title.value.isNullOrBlank() -> "제목을"
                    content.value.isNullOrBlank() -> "내용을"
                    else -> null
                }

                if (errorMsg != null) {
                    MessageUtils.showFailDialog(requireActivity(), "$errorMsg 입력해주세요.")
                } else {
                    Log.d(TAG, "start: $startTime, due: $dueTime")
                    createContest(prizeViewModel.prizeList.value!!)
                }
            }
        }

        binding.btnSelectPhoto.setOnClickListener {
            ImagePicker.multipleSelectStart(resultLauncher)
        }

        binding.etPrize.setOnClickListener {
            navController.navigate(CreateContestFragmentDirections.toPrizeFragment())
        }

        binding.etDate.setOnClickListener {
            showCalendar()
        }
    }

    override fun observerViewModel() {
        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner) {
                when(it) {
                    null -> MessageUtils.showToast(requireContext(), getString(R.string.error_server))
                    else -> navController.popBackStack()
                }
            }

            isLoading.observe(viewLifecycleOwner) {
                if(it) showProgress() else dismissProgress()
            }

            isFailure.observe(viewLifecycleOwner) {
            }

            ImagePicker.imageList.observe(viewLifecycleOwner) {
                imageAdapter.setList(it)
            }
        }
    }

    private fun showCalendar() {
        val calendar = MaterialDatePicker.Builder.dateRangePicker().setTitleText("대회 기간을 선택해주세요.").build()
        calendar.apply {
            isCancelable = false
            addOnPositiveButtonClickListener {
                viewModel.startTime.value = it.first
                viewModel.dueTime.value = it.second
                viewModel.date.value = "${it.first.getDateAsString(DATE)} ~ ${it.second.getDateAsString(DATE)}"
            }
        }
        calendar.show(parentFragmentManager, "Calendar")
    }
}

