package com.miftah.tedednew.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miftah.core.domain.model.StoryResult
import com.miftah.tedednew.databinding.CardStoryBinding

class AdapterCardStories :
    PagingDataAdapter<StoryResult, AdapterCardStories.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnClickListener

    inner class ViewHolder(val binding: CardStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listStoryItem: StoryResult) {
            Glide.with(binding.root)
                .load(listStoryItem.photoUrl)
                .into(binding.ivItemPhoto)
            binding.tvItemName.text = listStoryItem.name
            binding.tvItemDesc.text = listStoryItem.description
        }

        fun callCard(listStoryItem: StoryResult) {
            onItemClickCallback.onClickCard(listStoryItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*val listStoryItem = getItem(position)
        listStoryItem?.let {
            holder.bind(it)
            holder.itemView.setOnClickListener {
                holder.callCard(listStoryItem)
            }
        }*/
    }

    fun setOnClickCallback(call: OnClickListener) {
        this.onItemClickCallback = call
    }

    interface OnClickListener {
        fun onClickCard(friendItem: StoryResult)
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