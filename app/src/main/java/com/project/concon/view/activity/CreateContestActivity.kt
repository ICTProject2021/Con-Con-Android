package com.project.concon.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.concon.R
import com.project.concon.viewmodel.CreateContestViewModel
import com.project.concon.viewmodel.PrizeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateContestActivity : AppCompatActivity() {
    private val prizeViewModel: PrizeViewModel by viewModel()
    private val createContestViewModel: CreateContestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contest)
    }
}