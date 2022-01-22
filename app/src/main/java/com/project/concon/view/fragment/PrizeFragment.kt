package com.project.concon.view.fragment

import androidx.fragment.app.viewModels
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentPrizeBinding
import com.project.concon.view.dialog.PrizeDialogFragment
import com.project.concon.viewmodel.CreateContestViewModel
import com.project.concon.viewmodel.PrizeViewModel
import com.project.concon.widget.recyclerview.adapter.RecyclerViewPrizeAdapter
import dagger.android.support.AndroidSupportInjection.inject
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PrizeFragment : BaseFragment<FragmentPrizeBinding, PrizeViewModel>() {

    private val adapter = RecyclerViewPrizeAdapter()
    private val viewModel: PrizeViewModel by viewModel()
    private val createContestViewModel: CreateContestViewModel by viewModel()

//    override fun getViewModelClass(): Class<PrizeViewModel> = PrizeViewModel::class.java

    override fun init() {
        binding.rvPrize.adapter = adapter
        adapter.setOnItemClickListener {
            removePrize(it)
        }
    }

    override fun observerViewModel() {
        with(viewModel) {
            onAddEvent.observe(this@PrizeFragment) {
                PrizeDialogFragment().show(parentFragmentManager, "PrizeDialog")
            }

            onBackEvent.observe(this@PrizeFragment) {
                createContestViewModel.prize.value = "총 ${getTotalPrize()}원 "
                navController.popBackStack()
            }

            prizeList.observe(this@PrizeFragment) {
                adapter.setList(it)
            }
        }
    }

    private fun getTotalPrize(): String {
        var result = 0
        viewModel.prizeList.value?.forEach {
            result += it.price
        }

        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault()) // 돈 자릿수따라 콤마 처
        return numberFormat.format(result)
    }

    private fun removePrize(index: Int) {
        viewModel.prizeList.apply {
            value = value!!.filter { it != value!![index] }
        }
    }
}