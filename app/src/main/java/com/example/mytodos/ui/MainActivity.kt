package com.example.mytodos.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mytodos.R
import com.example.mytodos.ui.base.BaseActivity
import com.example.mytodos.utils.createChannel

class MainActivity : BaseActivity() {
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup navController
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
       // set the graph to appbar
        var appBarConfig = AppBarConfiguration(navController.graph)
        //attach action bar with navigation component
        setupActionBarWithNavController(navController, appBarConfig)
        // create notification channel
        createChannel(getString(R.string.channel_id), getString(R.string.channel_name), this)
    }

    override fun onSupportNavigateUp(): Boolean {
        // navigate to the first fragment in backstack
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}