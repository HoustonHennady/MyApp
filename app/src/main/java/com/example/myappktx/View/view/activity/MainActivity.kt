package com.example.myappktx.View.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.myappktx.R
import com.example.myappktx.ViewModels.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()
    }

    private fun initialization() {
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(bottom_nav_view, navController)
        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        lifecycle.addObserver(viewModel)
    }
}