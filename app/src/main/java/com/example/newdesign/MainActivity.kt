package com.example.newdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    companion object {
        var instance: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this
        setupNavigationBottom()
    }


    private fun setupNavigationBottom() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController

//      val appConfig= AppBarConfiguration(setOf(R.id.chooseLanguageFragment,R.id.splashFragment))
//       setupActionBarWithNavController(navController,appConfig)
    }


//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController,null)||super.onSupportNavigateUp()
//    }


    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
}