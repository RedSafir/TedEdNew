package com.miftah.tedednew.app.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miftah.core.domain.model.StoryResult
import com.miftah.core.utils.Constants
import com.miftah.tedednew.app.ui.AdapterCardStories
import com.miftah.tedednew.app.ui.LoadingStateAdapter
import com.miftah.tedednew.databinding.FragmentListStoryBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class ListStoryFragment : Fragment() {

    private var _binding: FragmentListStoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterCardStories
    private val viewModel: MainViewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRV()

        viewModel.getAllStories().observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(view?.context, RecyclerView.VERTICAL, false)
        adapter = AdapterCardStories()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        binding.rvStories.layoutManager = layoutManager
        binding.rvStories.addItemDecoration(
            DividerItemDecoration(view?.context, layoutManager.orientation)
        )
        adapter.setOnClickCallback(object : AdapterCardStories.OnClickListener {
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

    override fun onStart() {
        adapter.refresh()
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvStories.adapter = null
        _binding = null
    }

}