package com.kakaobank.imgsurfer.presentation.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import com.kakaobank.imgsurfer.R
import com.kakaobank.imgsurfer.databinding.FragmentSearchBinding
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.presentation.ContentViewModel
import com.kakaobank.imgsurfer.presentation.adapter.SearchResultPagingAdapter
import com.kakaobank.imgsurfer.presentation.type.UiStateType
import com.kakaobank.imgsurfer.util.binding.BindingFragment
import com.kakaobank.imgsurfer.util.extension.collectFlow
import com.kakaobank.imgsurfer.util.extension.showKeyboard
import com.kakaobank.imgsurfer.util.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: ContentViewModel by activityViewModels()
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
        searchAdapter = SearchResultPagingAdapter(::updateHeartState,
            viewModel::isArchivedContent,
            ::moveToDetail)
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

        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            viewModel.hasFocusingSearchBar.value = hasFocus
        }

        searchAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.Loading -> viewModel.setSearchState(UiStateType.LOADING)
                is LoadState.NotLoading -> {
                    if (searchAdapter.itemCount > 0) viewModel.setSearchState(UiStateType.SUCCESS)
                    else viewModel.setSearchState(UiStateType.EMPTY)
                }
                is LoadState.Error -> viewModel.setSearchState(UiStateType.ERROR)
            }
        }
    }

    private fun collectData() {
        collectFlow(viewModel.searchResult) {
            searchAdapter.submitData(it)
        }
    }

    private fun searchContent(keyword: String) {
        if (keyword.isBlank()) requireContext().showToast(getString(R.string.search_keyword_input_request_toast_message))
        else viewModel.searchContent(keyword)
    }

    /** 검색 결과 콘텐츠의 보관 상태를 업데이트. 보관 -> 보관해제, 보관해제 -> 보관 처리함. */
    private fun updateHeartState(content: Content?, isSelected: Boolean) {
        if (content == null) return
        viewModel.updateHeartState(content, isSelected)
    }

    private fun moveToDetail(content: Content) {
        Intent(requireContext(), ContentDetailActivity::class.java).apply {
            putExtra(ContentDetailActivity.ARG_TITLE, content.source)
            putExtra(ContentDetailActivity.ARG_IMAGE_URL,
                content.imageUrl
                    ?: content.thumbnailUrl) // 동영상은 원본 이미지 url을 받아올 수 없기 때문에 디테일뷰에서 thumbnailUrl을 보여줌.
        }.also {
            startActivity(it)
        }
    }
}
