package com.example.a2021ictproject.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a2021ictproject.R
import com.example.a2021ictproject.adapter.JoinContestImageRecyclerViewAdapter
import com.example.a2021ictproject.adapter.JoinContestRecyclerViewAdapter
import com.example.a2021ictproject.bind.submitList
import com.example.a2021ictproject.databinding.JoinContestFragmentBinding
import com.example.a2021ictproject.network.dto.response.Participant
import com.example.a2021ictproject.utils.MessageUtils
import com.example.a2021ictproject.viewmodel.JoinContestViewModel
import java.io.IOException

class JoinContestFragment : Fragment() {

    private val navController by lazy { findNavController() }

    private lateinit var binding: JoinContestFragmentBinding
    private val viewModel: JoinContestViewModel by activityViewModels()

    private val navArgs by navArgs<JoinContestFragmentArgs>()
    private val adapter = JoinContestImageRecyclerViewAdapter()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.join_contest_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.participantList = ObservableArrayList()
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observe()

        binding.btnBackJoinContest.setOnClickListener {
            navigateToContestDetail()
        }

        binding.btnDeleteJoinContest.setOnClickListener {
            // 파일 리스트에 저장된 아이템 삭제
            viewModel.fileList.value = listOf()
        }

        binding.btnPhotoJoinContest.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            resultLauncher.launch(Intent.createChooser(intent, "최대 5장까지 선택 가능"), )
        }

        binding.btnSendJoinContest.setOnClickListener {
            viewModel.postParticipant()
        }
    }

    private fun init() {
        binding.rvParticipantJoinContest.adapter = JoinContestRecyclerViewAdapter()
        binding.rvJoinJoinContest.adapter = adapter
        viewModel.getParticipantInfo(navArgs.id)

        // 대충 이미지 처리하는 로직
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                try {
                    if (it.data?.clipData != null) {
                        Log.d("resultLauncher", "11")
                        // 다중 선택일 때
                        val count = it.data!!.clipData!!.itemCount

                        if (count > 5) {
                            MessageUtils.showDialog(requireContext(), "알림", "최대 선택할 수 있는 사진의 갯수는 5장입니다.")
                        } else {
                            val list = mutableListOf<Uri>()
                            for (i in 0 until count) {
                                list.add(it.data!!.clipData!!.getItemAt(i).uri)
                            }

                            viewModel.fileList.value = list
                        }
                    } else if (it.data?.data != null) {
                        // 단일 선택 일 때
                        viewModel.fileList.value = listOf(it.data?.data!!)
                    }
                } catch(e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun observe() = with(viewModel) {
        getParticipantInfoRes.observe(viewLifecycleOwner) {
            binding.rvParticipantJoinContest.submitList(it!!)
        }

        postParticipantRes.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "대회 참가 성공", Toast.LENGTH_SHORT).show()
        }

        fileList.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
    }

    private fun navigateToContestDetail() {
        navController.navigate(JoinContestFragmentDirections.actionJoinContestFragmentToContestDetailFragment(navArgs.id))
    }


}