package com.project.concon.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.concon.R
import com.project.concon.adapter.RecyclerViewJoinContestAdapter
import com.project.concon.adapter.RecyclerViewJoinContestImageAdapter
import com.project.concon.databinding.JoinContestFragmentBinding
import com.project.concon.utils.MessageUtils
import com.project.concon.viewmodel.JoinContestViewModel
import java.io.IOException

class JoinContestFragment : Fragment() {

    private val navController by lazy { findNavController() }

    private lateinit var binding: JoinContestFragmentBinding
    private val viewModel: JoinContestViewModel by activityViewModels()

    private val navArgs by navArgs<JoinContestFragmentArgs>()

    private val imgAdapter = RecyclerViewJoinContestImageAdapter()
    private val joinAdapter = RecyclerViewJoinContestAdapter()

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
            viewModel.postParticipant(navArgs.id, requireContext().contentResolver)
        }

        // 리사이클러뷰 좋아요 클릭리스너
        joinAdapter.onClickJoinListener {
            viewModel.putLikes(navArgs.id, it)
        }
    }

    private fun init() {
        binding.rvParticipantJoinContest.adapter = joinAdapter
        binding.rvImgJoinContest.adapter = imgAdapter
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
            joinAdapter.setList(it!!)
        }

        postParticipantRes.observe(viewLifecycleOwner) {
            content.value = ""
            getParticipantInfo(navArgs.id)
        }

        isSuccessPutLikes.observe(viewLifecycleOwner) {

        }

        fileList.observe(viewLifecycleOwner) {
            imgAdapter.setList(it)
        }

        isLoading.observe(viewLifecycleOwner) {
            if (it) {
                MessageUtils.showProgress(requireActivity())
            } else {
                MessageUtils.dismissProgress()
            }
        }
    }

    private fun navigateToContestDetail() {
        navController.navigate(JoinContestFragmentDirections.actionJoinContestFragmentToContestDetailFragment(navArgs.id))
    }

}