package com.project.concon.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.concon.R
import com.project.concon.databinding.FragmentDialogUpdateBinding

class DialogFragmentUpdate(private val content: String) : DialogFragment() {

    private lateinit var binding: FragmentDialogUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvContent.text = "${content}는 추후에 업데이트 예정입니다."

        binding.btnConfirm.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogUpdateBinding.inflate(requireActivity().layoutInflater)

        val dialog: AlertDialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()

        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }
}