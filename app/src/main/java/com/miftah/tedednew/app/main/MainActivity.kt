package com.miftah.tedednew.app.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.miftah.tedednew.R
import com.miftah.tedednew.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()

        viewModel.getSession().observe(this) {
            if (!it.isLogin) {
                val uri = Uri.parse("tedednew://auth")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                finish()
            }
        }
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.navHost)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_favorite -> {
                val uri = Uri.parse("tedednew://fav")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }

            android.R.id.home -> {
                findNavController(R.id.navHost).popBackStack()
                true
            }

            else -> {
                false
            }
        }

    }
}