package com.example.projectg17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setup the bottom navigation menu so it responds to clicks
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container1) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            findViewById<BottomNavigationView>(R.id.bottomNavigationView),
            navController
        )
    }
}