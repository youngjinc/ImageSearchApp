package com.kakaobank.imgsurfer.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakaobank.imgsurfer.databinding.ItemArchiveBinding
import com.kakaobank.imgsurfer.domain.model.Content

class ArchivedContentAdapter(private val onHeartClickListener: (Content?, Boolean) -> Unit) :
    ListAdapter<Content, ArchivedContentAdapter.ArchivedContentViewHolder>(
        object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem.imageUrl == newItem.imageUrl

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem == newItem
        }
    ) {
    private lateinit var inflater: LayoutInflater

    class ArchivedContentViewHolder(private val binding: ItemArchiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Content, onHeartClickListener: (Content?, Boolean) -> Unit) {
            data.let { binding.content = it }
            binding.ivHeart.isSelected = true
            binding.ivHeart.setOnClickListener {
                it.isSelected = !it.isSelected
                onHeartClickListener(data, it.isSelected)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivedContentViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return ArchivedContentViewHolder(ItemArchiveBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ArchivedContentViewHolder, position: Int) {
        holder.onBind(currentList[position], onHeartClickListener)
    }
}
