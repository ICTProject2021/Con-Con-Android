package com.example.a2021ictproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            initNavigation()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.mainFragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}