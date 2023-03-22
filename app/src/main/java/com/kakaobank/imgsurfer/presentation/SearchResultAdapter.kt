package com.kakaobank.imgsurfer.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kakaobank.imgsurfer.databinding.ItemContentBinding
import com.kakaobank.imgsurfer.domain.model.Content

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    private lateinit var inflater: LayoutInflater
    private var results: List<Content> = mutableListOf()

    class SearchResultViewHolder(private val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Content) {
            binding.content = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return SearchResultViewHolder(ItemContentBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.onBind(results[position])
    }

    override fun getItemCount() = results.size

    fun setResults(results: List<Content>) {
        this.results = results
        notifyDataSetChanged()
    }
}
