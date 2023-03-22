package com.kakaobank.imgsurfer.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kakaobank.imgsurfer.databinding.ItemContentBinding
import com.kakaobank.imgsurfer.domain.model.Content

class SearchResultPagingAdapter :
    PagingDataAdapter<Content, SearchResultPagingAdapter.SearchResultViewHolder>(  //PagingDataAdapter
        object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem.imageUrl == newItem.imageUrl

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem == newItem
        }
    ) {
    private lateinit var inflater: LayoutInflater

    class SearchResultViewHolder(private val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Content?) {
            data?.let { binding.content = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return SearchResultViewHolder(ItemContentBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
