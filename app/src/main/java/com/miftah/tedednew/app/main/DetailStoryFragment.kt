package com.miftah.tedednew.app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miftah.tedednew.databinding.FragmentDetailStoryBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class DetailStoryFragment : Fragment() {
    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val name = DetailStoryFragmentArgs.fromBundle(arguments as Bundle).name
        val photoUrl = DetailStoryFragmentArgs.fromBundle(arguments as Bundle).photoUrl
        val description = DetailStoryFragmentArgs.fromBundle(arguments as Bundle).description
        val id = DetailStoryFragmentArgs.fromBundle(arguments as Bundle).idStory

        viewModel.setStory(photoUrl, description, name, id)

        viewModel.storyResult.observe(viewLifecycleOwner) {
            binding.tvDetailName.text = it.name
            Glide.with(binding.root)
                .load(it.photoUrl)
                .into(binding.ivDetailPhoto)
            binding.tvDetailDescription.text = it.description
        }

        viewModel.isStorySave(id).observe(viewLifecycleOwner) {
            if(it) {
                binding.fabFav.setImageResource(R.drawable.baseline_favorite_24)
                binding.fabFav.setOnClickListener {
                    viewModel.deleteStory()
                }
            } else {
                binding.fabFav.setImageResource(R.drawable.baseline_favorite_border_24)
                binding.fabFav.setOnClickListener {
                    viewModel.saveStory()
                }
            }
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}