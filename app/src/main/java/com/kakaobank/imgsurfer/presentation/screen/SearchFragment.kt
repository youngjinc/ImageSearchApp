package com.kakaobank.imgsurfer.presentation.screen

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import com.kakaobank.imgsurfer.R
import com.kakaobank.imgsurfer.databinding.FragmentSearchBinding
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.presentation.SearchViewModel
import com.kakaobank.imgsurfer.presentation.adapter.SearchResultPagingAdapter
import com.kakaobank.imgsurfer.presentation.type.EmptyViewType
import com.kakaobank.imgsurfer.util.binding.BindingFragment
import com.kakaobank.imgsurfer.util.extension.collectFlow
import com.kakaobank.imgsurfer.util.extension.showKeyboard
import com.kakaobank.imgsurfer.util.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var searchAdapter: SearchResultPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initLayout()
        addListener()
        collectData()
    }

    private fun initLayout() {
        searchAdapter = SearchResultPagingAdapter(::updateHeartState, viewModel::isArchivedContent)
        binding.rvSearchResult.adapter = searchAdapter
    }

    private fun addListener() {
        binding.root.setOnClickListener {
            viewModel.reloadInputKeyword()
            requireContext().showKeyboard(it, false)
            binding.etSearch.clearFocus()
        }

        binding.etSearch.setOnEditorActionListener { keyword, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE) {
                searchContent(keyword.text.toString())
                binding.etSearch.clearFocus()
            }
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

    private fun searchContent(keyword: String) {
        if (keyword.isBlank()) requireContext().showToast(getString(R.string.search_keyword_input_request_toast_message))
        else viewModel.searchContent(keyword)
    }

    private fun updateHeartState(content: Content?, isSelected: Boolean) {
        if (content == null) return
        viewModel.updateHeartState(content, isSelected)
    }
}
