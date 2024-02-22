package com.miftah.tedednew.app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftah.core.domain.model.StoryResult
import com.miftah.tedednew.app.ui.AdapterFavStories
import com.miftah.tedednew.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModel<MainViewModel>()

    private lateinit var adapter: AdapterFavStories

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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
                val toDetailStoryFragment =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailStoryFragment().apply {
                        this.name = storyResult.name
                        this.photoUrl = storyResult.photoUrl
                        this.description = storyResult.description
                        this.idStory = storyResult.id
                    }
                findNavController().navigate(toDetailStoryFragment)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}