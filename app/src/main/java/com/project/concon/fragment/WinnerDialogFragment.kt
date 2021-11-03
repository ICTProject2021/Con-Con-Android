package com.project.concon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.concon.R
import com.project.concon.databinding.FragmentWinnerDialogBinding

class WinnerDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentWinnerDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWinnerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

}