package com.kakaobank.imgsurfer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kakaobank.imgsurfer.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {
    val inputKeyword = MutableStateFlow("")
    private val validKeyword = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResult = validKeyword.flatMapLatest {
        if (it.isNotBlank()) searchRepository.searchContent(it)
        else flow {}
    }.cachedIn(viewModelScope)

    fun searchContent(keyword: String) {
        validKeyword.value = keyword
    }

    fun clearInputKeyword() {
        inputKeyword.value = ""
    }
}
