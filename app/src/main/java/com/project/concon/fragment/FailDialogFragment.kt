package com.project.concon.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.concon.databinding.FragmentFailDialogBinding

class FailDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentFailDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.isCancelable = false
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = FragmentFailDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button11.setOnClickListener {
            this.dismiss()
        }
    }

    fun setContent(content: String): DialogFragment {
        binding.tvContentDialogFail.text = content
        return this
    }
}