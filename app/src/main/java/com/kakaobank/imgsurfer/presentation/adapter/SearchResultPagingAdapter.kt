package com.kakaobank.imgsurfer.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kakaobank.imgsurfer.databinding.ItemSearchBinding
import com.kakaobank.imgsurfer.domain.model.Content

class SearchResultPagingAdapter(
    private val onHeartClickListener: (Content?, Boolean) -> Unit,
    private val isArchivedContent: (Content?) -> Boolean,
    private val moveToDetail: (Content) -> Unit,
) :
    PagingDataAdapter<Content, SearchResultPagingAdapter.SearchResultViewHolder>(
        object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem.imageUrl == newItem.imageUrl

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem == newItem
        }
    ) {
    private lateinit var inflater: LayoutInflater

    class SearchResultViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            data: Content?,
            onHeartClickListener: (Content?, Boolean) -> Unit,
            isArchivedContent: (Content?) -> Boolean,
            moveToDetail: (Content) -> Unit,
        ) {
            data?.let { binding.content = it }
            binding.ivHeart.isSelected = isArchivedContent(data)
            binding.ivHeart.setOnClickListener {
                it.isSelected = !it.isSelected
                onHeartClickListener(data, it.isSelected)
            }
            binding.root.setOnClickListener {
                data?.let { moveToDetail(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return SearchResultViewHolder(ItemSearchBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.onBind(getItem(position), onHeartClickListener, isArchivedContent, moveToDetail)
    }
}
