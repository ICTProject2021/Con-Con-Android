package com.project.concon.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.concon.databinding.DialogFragmentDefaultBinding

class DefaultDialogFragment(
    private val title: String,
    private val content: String
) : DialogFragment() {

    private lateinit var binding: DialogFragmentDefaultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = title
        binding.tvContent.text = content

        binding.btnConfirm.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentDefaultBinding.inflate(requireActivity().layoutInflater)

        val dialog: AlertDialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()

        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }
}