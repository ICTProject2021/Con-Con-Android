package com.example.a2021ictproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.a2021ictproject.databinding.FragmentPrizeDialogBinding
import com.example.a2021ictproject.viewmodel.DialogViewModel

class PrizeDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentPrizeDialogBinding
    private val viewModel : DialogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrizeDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            if (binding.price.text.isEmpty()) {
                TODO()
            } else {
                viewModel.setPrize(Integer.parseInt(binding.price.text.toString()))
                dismiss()
            }
        }
    }
}