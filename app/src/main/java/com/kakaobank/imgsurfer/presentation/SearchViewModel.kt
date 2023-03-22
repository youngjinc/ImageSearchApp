package com.kakaobank.imgsurfer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.domain.repository.SearchRepository
import com.kakaobank.imgsurfer.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {
    private var _searchUiState = MutableStateFlow<UiState<List<Content>>>(UiState.Init)
    val searchUiState get() = _searchUiState.asStateFlow()
    val searchKeyword = MutableStateFlow("")

    fun searchContent() {
        viewModelScope.launch {
            searchRepository.searchImage(searchKeyword.value)
                .onSuccess {
                    _searchUiState.value =
                        if (it.isEmpty()) UiState.Empty else UiState.Success(it)
                }.onFailure {
                    _searchUiState.value = UiState.Error(it.message)
                }
        }
    }
}
