package com.example.a2021ictproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.FragmentPrizeBinding

class PrizeFragment : Fragment() {

    private lateinit var binding: FragmentPrizeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPrizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addPrizeButton.setOnClickListener {
            PrizeDialogFragment().show(
                parentFragmentManager, "PrizeDialog"
            )
        }
    }

}