package com.example.teamprojectapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.teamprojectapplication.databinding.ActivityMainBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    val viewModel: PostsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topNav)
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        binding.bottomNav.setupWithNavController(navController)



        binding.topNav.setOnMenuItemClickListener {
            val currentDestinationId = navController.currentDestination?.id
            when (currentDestinationId) {
                R.id.homeFragment -> {
                    viewModel.setPost()
                    navController.navigate(R.id.action_homeFragment_to_addDdayFragment) 
                    true
                }
                R.id.communityFragment -> {
                    viewModel.setPost()
                    navController.navigate(R.id.action_communityFragment_to_addDdayFragment)
                    true
                }
                R.id.postFragment -> {
                    viewModel.setPost()
                    navController.navigate(R.id.action_postFragment_to_addDdayFragment)
                    true
                }
                R.id.searchFragment -> {
                    viewModel.setPost()
                    navController.navigate(R.id.action_searchFragment_to_addDdayFragment)
                    true
                }
                else -> false
            }
        }

        binding.topNav.inflateMenu(R.menu.menu_top)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        val currentDestinationId = navController.currentDestination?.id

        // 특정 프레그먼트에서만 메뉴를 표시하도록 조건을 추가
        when (currentDestinationId) {
            R.id.homeFragment, R.id.communityFragment, R.id.postFragment, R.id.searchFragment -> {
                menuInflater.inflate(R.menu.menu_top, menu)
                return true
            }
            else -> return false
        }
    }

}