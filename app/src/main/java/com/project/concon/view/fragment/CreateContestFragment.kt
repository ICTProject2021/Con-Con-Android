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
import com.project.concon.widget.extension.getDateAsString
import com.project.concon.widget.recyclerview.adapter.RecyclerViewJoinContestImageAdapter
import com.project.concon.widget.utils.ImagePicker
import com.project.concon.widget.utils.MessageUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CreateContestFragment : BaseFragment<FragmentCreateContestBinding, CreateContestViewModel>() {

    companion object {
        private const val TAG = "CreateContestFragment"
    }

    override val viewModel: CreateContestViewModel by viewModel()
    private val prizeViewModel: PrizeViewModel by viewModels()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val imageAdapter = RecyclerViewJoinContestImageAdapter()

    override fun init() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            ImagePicker.init(it)
        }

        binding.rvPhotoCreateContest.adapter = imageAdapter
    }

    override fun observerViewModel() {
        with(viewModel) {
            onCloseEvent.observe(this@CreateContestFragment) {
                navController.popBackStack()
            }

            onConfirmEvent.observe(this@CreateContestFragment) {
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

            onSelectPhotoEvent.observe(this@CreateContestFragment) {
                ImagePicker.multipleSelectStart(resultLauncher)
            }

            onSetPrizeEvent.observe(this@CreateContestFragment) {
                navController.navigate(CreateContestFragmentDirections.toPrizeFragment())
            }

            onSetDateEvent.observe(this@CreateContestFragment) {
                showCalendar()
            }

            isSuccess.observe(this@CreateContestFragment) {
                when (it) {
                    null -> MessageUtils.showToast(requireContext(), getString(R.string.error_server))
                    else -> navController.popBackStack()
                }
            }

            isLoading.observe(this@CreateContestFragment) {
                if (it) MessageUtils.showProgress(requireActivity())
                else MessageUtils.dismissProgress()
            }

            isFailure.observe(this@CreateContestFragment) {
            }

            ImagePicker.imageList.observe(this@CreateContestFragment) {
                imageAdapter.setList(it)
            }
        }
    }

    private fun showCalendar() {
        MaterialDatePicker.Builder.dateRangePicker().setTitleText("대회 기간을 선택해주세요.").build().apply {
            isCancelable = false
            addOnPositiveButtonClickListener {
                viewModel.startTime.value = it.first
                viewModel.dueTime.value = it.second
                viewModel.date.value = "${it.first.getDateAsString(DATE)} ~ ${it.second.getDateAsString(DATE)}"
            }
            show(requireActivity().supportFragmentManager, "Calendar")
        }
    }
}

