package com.miftah.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.miftah.core.utils.Constants
import com.miftah.detail.databinding.ActivityDetailStoryBinding
import com.miftah.detail.di.detailModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private val viewModel: DetailStoryViewModel by viewModel<DetailStoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(detailModule)

        val id = intent?.data?.getQueryParameter(Constants.STORY_ID) as String
        val description = intent?.data?.getQueryParameter(Constants.STORY_DESCRIPTION) as String
        val title = intent?.data?.getQueryParameter(Constants.STORY_TITLE) as String
        val photoUrl = intent?.data?.getQueryParameter(Constants.STORY_PHOTO_URL) as String

        viewModel.setStory(photoUrl, description, title, id)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(binding.favFragmentContainer.id, DetailStoryFragment())
        }
    }
}