package com.project.concon.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.project.concon.R
import com.project.concon.databinding.ActivityMainBinding
import com.project.concon.widget.extension.startActivity

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
            startActivity(CreateContestActivity::class.java)
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