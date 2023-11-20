package com.example.teamprojectapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.teamprojectapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.topNav)
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        binding.bottomNav.setupWithNavController(navController)

        setContentView(binding.root)

        binding.topNav.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.addDdayFragment -> {
                    //binding.topNav.setupWithNavController(navController)

                    //if 문으로 현재 프레그먼트가 무엇이었는지 판단하기
                    navController.navigate(R.id.action_homeFragment_to_addDdayFragment)
                    //navController.navigate(R.id.action_communityFragment_to_addDdayFragment)
                    /*navController.navigate(R.id.action_postFragment_to_addDdayFragment)
                    navController.navigate(R.id.action_searchFragment_to_addDdayFragment)
                     */
                    true
                }
                else -> false
            }
        }

        binding.topNav.inflateMenu(R.menu.menu_top)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_top, menu)
        return true
    }




}