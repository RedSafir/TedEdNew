package com.miftah.tedednew.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miftah.core.domain.model.StoryResult
import com.miftah.tedednew.databinding.CardStoryBinding

class AdapterFavStories : ListAdapter<StoryResult, AdapterFavStories.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: IOnClickListener

    inner class ViewHolder(val binding: CardStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listStoryItem: StoryResult) {
            Glide.with(binding.root)
                .load(listStoryItem.photoUrl)
                .into(binding.ivItemPhoto)
            binding.tvItemName.text = listStoryItem.name
            binding.tvItemDesc.text = listStoryItem.description
        }

        fun callCard(storyResult: StoryResult) {
            onItemClickCallback.onClickCard(storyResult)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friendList = getItem(position)
        holder.bind(friendList)
        holder.itemView.setOnClickListener {
            holder.callCard(friendList)
        }
    }

    fun setOnClickCallback(call: IOnClickListener) {
        this.onItemClickCallback = call
    }

    interface IOnClickListener {
        fun onClickCard(storyResult: StoryResult)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResult>() {
            override fun areItemsTheSame(
                oldItem: StoryResult,
                newItem: StoryResult
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoryResult,
                newItem: StoryResult
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
