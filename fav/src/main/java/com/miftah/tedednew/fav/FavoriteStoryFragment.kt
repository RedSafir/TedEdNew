package com.miftah.tedednew.fav

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftah.core.domain.model.StoryResult
import com.miftah.core.utils.Constants
import com.miftah.tedednew.fav.databinding.FragmentFavoriteStoryBinding
import com.miftah.tedednew.fav.ui.AdapterFavStories
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoriteStoryFragment : Fragment() {


    private var _binding : FragmentFavoriteStoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteStoryViewModel by activityViewModel<FavoriteStoryViewModel>()

    private lateinit var adapter: AdapterFavStories

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()

        viewModel.getAllSavedStories().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupRv() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterFavStories()
        binding.rvFavStories.adapter = adapter
        binding.rvFavStories.layoutManager = layoutManager
        binding.rvFavStories.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        adapter.setOnClickCallback(object : AdapterFavStories.IOnClickListener {
            override fun onClickCard(storyResult: StoryResult) {
                val name = storyResult.name
                val photoUrl = storyResult.photoUrl
                val description = storyResult.description
                val idStory = storyResult.id
                val data =
                    Uri.parse("tedednew://detail?${Constants.STORY_ID}=$idStory&${Constants.STORY_PHOTO_URL}=$photoUrl&${Constants.STORY_DESCRIPTION}=$description&${Constants.STORY_TITLE}=$name")
                startActivity(Intent(Intent.ACTION_VIEW, data))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}