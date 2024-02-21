package com.miftah.tedednew.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miftah.tedednew.auth.databinding.ActivityWelcomeBinding
import com.miftah.tedednew.auth.di.authModule
import org.koin.core.context.loadKoinModules

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(authModule)

        supportActionBar?.hide()
    }
}