package com.project.concon.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentJoinContestBinding
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.recyclerview.adapter.RecyclerViewJoinContestAdapter
import com.project.concon.widget.recyclerview.adapter.RecyclerViewJoinContestImageAdapter
import com.project.concon.viewmodel.JoinContestViewModel
import java.io.IOException
import org.koin.androidx.viewmodel.ext.android.viewModel

class JoinContestFragment : BaseFragment<FragmentJoinContestBinding, JoinContestViewModel>() {

    // TODO(아래 문제는 내일의 나에게~~)
//    override fun setBinding() {
//        binding.participantList = ObservableArrayList()
//        binding.vm = viewModel
//    }

    private val navArgs by navArgs<JoinContestFragmentArgs>()

    private val imgAdapter = RecyclerViewJoinContestImageAdapter()
    private val joinAdapter = RecyclerViewJoinContestAdapter()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override val viewModel: JoinContestViewModel by viewModel()

    override fun init() {
        binding.rvParticipantJoinContest.adapter = joinAdapter
        binding.rvImgJoinContest.adapter = imgAdapter

        viewModel.getParticipantInfo(navArgs.id)

        // 대충 이미지 처리하는 로직
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                try {
                    if (it.data?.clipData != null) { // 다중 선택일 때
                        Log.d("resultLauncher", "11")
                        val count = it.data!!.clipData!!.itemCount

                        if (count > 5) {
//                            MessageUtils.showDialog(requireContext(), "알림", "최대 선택할 수 있는 사진의 갯수는 5장입니다.")
                        } else {
                            val list = mutableListOf<Uri>()
                            for (i in 0 until count) {
                                list.add(it.data!!.clipData!!.getItemAt(i).uri)
                            }

                            viewModel.fileList.value = list
                        }
                    } else if (it.data?.data != null) { // 단일 선택 일 때
                        viewModel.fileList.value = listOf(it.data?.data!!)
                    }
                } catch(e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        // 리사이클러뷰 좋아요 클릭리스너
        joinAdapter.onClickJoinListener {
            viewModel.like(navArgs.id, it)
        }
    }

    override fun observerViewModel() {
        // TODO(isFailure.observe() 추가하기)
        with(viewModel) {
            onBackEvent.observe(this@JoinContestFragment) {
                navController.navigate(JoinContestFragmentDirections.toContestDetailFragment(navArgs.id))
            }

            onSendEvent.observe(this@JoinContestFragment) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                resultLauncher.launch(Intent.createChooser(intent, "최대 5장까지 선택 가능"), )
            }

            onSelectPhotoEvent.observe(this@JoinContestFragment) {
                participate(navArgs.id, requireContext().contentResolver)
            }

            isSuccessGetParticipantInfo.observe(viewLifecycleOwner) {
                joinAdapter.setList(it)
            }

            isSuccessPostParticipate.observe(viewLifecycleOwner) {
                content.value = ""
                getParticipantInfo(navArgs.id)
            }

            isSuccessPutLikes.observe(viewLifecycleOwner) {
                getParticipantInfo(navArgs.id)
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
    }
}