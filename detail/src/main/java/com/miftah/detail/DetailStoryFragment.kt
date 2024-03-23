package com.miftah.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.miftah.detail.databinding.FragmentDetailStoryBinding
import com.miftah.tedednew.R
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailStoryViewModel by activityViewModel<DetailStoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.storyResult.observe(viewLifecycleOwner) {
            binding.tvDetailName.text = it.name
            Glide.with(binding.root)
                .load(it.photoUrl)
                .into(binding.ivDetailPhoto)
            binding.tvDetailDescription.text = it.description
        }

        viewModel.storyResult.observe(viewLifecycleOwner) { data ->
            viewModel.isStorySave(data.id).observe(viewLifecycleOwner) {
                if (it) {
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
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}