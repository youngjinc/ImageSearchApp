package com.kakaobank.imgsurfer.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.kakaobank.imgsurfer.R
import com.kakaobank.imgsurfer.databinding.FragmentSearchBinding
import com.kakaobank.imgsurfer.presentation.SearchResultAdapter
import com.kakaobank.imgsurfer.presentation.SearchViewModel
import com.kakaobank.imgsurfer.util.UiState
import com.kakaobank.imgsurfer.util.binding.BindingFragment
import com.kakaobank.imgsurfer.util.extension.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by activityViewModels()
    private val searchAdapter = SearchResultAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initLayout()
        collectData()
    }

    private fun initLayout() {
        binding.rvSearch.adapter = searchAdapter
    }

    private fun collectData() {
        collectFlow(viewModel.searchUiState) { state ->
            when (state) {
                is UiState.Success -> searchAdapter.setResults(state.data)
                else -> {} // TODO empty 뷰 처리 및 예외처리 필요
            }
        }
    }
}
