package com.example.newdesign

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newdesign.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var context: Context

    companion object {
        var instance: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this
        setupNavigationBottom()
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.homeFragment,
                R.id.moreFragment,
                R.id.myScheduleFragment -> {
                    binding.bottomViewNav.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomViewNav.visibility = View.GONE

                }
            }
        }
    }


    private fun setupNavigationBottom() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration= AppBarConfiguration(

            setOf(R.id.homeFragment,R.id.moreFragment,R.id.myScheduleFragment)
        )
        //setupActionBarWithNavController(navController,appBarConfiguration)
        binding.bottomViewNav.setupWithNavController(navController)
//      val appConfig= AppBarConfiguration(setOf(R.id.chooseLanguageFragment,R.id.splashFragment))
//       setupActionBarWithNavController(navController,appConfig)
    }


//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController,null)||super.onSupportNavigateUp()
//    }


    override fun onSupportNavigateUp(): Boolean {
        return onNavigateUp()||super.onSupportNavigateUp()
    }
}