package com.kakaobank.imgsurfer.presentation.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import com.kakaobank.imgsurfer.R
import com.kakaobank.imgsurfer.databinding.FragmentSearchBinding
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.presentation.SearchResultPagingAdapter
import com.kakaobank.imgsurfer.presentation.SearchViewModel
import com.kakaobank.imgsurfer.presentation.type.EmptyViewType
import com.kakaobank.imgsurfer.util.binding.BindingFragment
import com.kakaobank.imgsurfer.util.extension.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by activityViewModels()
    private val searchAdapter = SearchResultPagingAdapter(::updateHeartState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initLayout()
        addListener()
        collectData()
    }

    private fun initLayout() {
        binding.rvSearch.adapter = searchAdapter
    }

    private fun addListener() {
        binding.etSearch.setOnEditorActionListener { keyword, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE)
                viewModel.searchContent(keyword.text.toString())
            false
        }

        searchAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && !loadState.source.refresh.endOfPaginationReached && searchAdapter.itemCount > 0)
                viewModel.searchState.value = EmptyViewType.NotEmpty
            else
                viewModel.searchState.value = EmptyViewType.Empty
        }
    }

    private fun collectData() {
        collectFlow(viewModel.searchResult) {
            searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun updateHeartState(content: Content?, isSelected: Boolean) {
        if (content == null) return
        viewModel.updateHeartState(content, isSelected)
    }
}
