package com.miftah.tedednew.fav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.miftah.tedednew.fav.databinding.ActivityFavoriteStoryBinding
import com.miftah.tedednew.fav.di.favModule
import org.koin.core.context.loadKoinModules

class FavoriteStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(favModule)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(binding.favFragmentContainer.id, FavoriteStoryFragment())
        }
    }
}