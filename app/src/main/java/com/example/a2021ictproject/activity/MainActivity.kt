package com.example.a2021ictproject.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            val currentFragment =
                NavHostFragment.findNavController(navHostFragment).currentDestination!!.id
            if (currentFragment == R.id.mainFragment) {
                navController.navigate(R.id.action_mainFragment_to_createContestFragment)
            } else if (currentFragment == R.id.profileFragment) {
                navController.navigate(R.id.action_profileFragment_to_createContestFragment)
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.mainFragment || destination.id == R.id.profileFragment) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.fab.visibility = View.VISIBLE
                binding.bottomAppBar.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
                binding.fab.visibility = View.GONE
                binding.bottomAppBar.visibility = View.GONE
            }
        }
    }
}