package com.kakaobank.imgsurfer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kakaobank.imgsurfer.data.datasource.LocalDataSource
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.domain.repository.SearchRepository
import com.kakaobank.imgsurfer.presentation.type.EmptyViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor( // TODO 클래스명 수정 필요
    private val searchRepository: SearchRepository,
    private val localStorage: LocalDataSource,
) : ViewModel() {
    val inputKeyword = MutableStateFlow("")
    private val validKeyword = MutableStateFlow("")
    val searchState = MutableStateFlow(EmptyViewType.Init)

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResult = validKeyword.flatMapLatest {
        if (it.isNotBlank()) searchRepository.searchContent(it)
        else flow {}
    }.cachedIn(viewModelScope)

    private val _archivedContents = MutableStateFlow(localStorage.contents)
    val archivedContents get() = _archivedContents.asStateFlow()

    fun searchContent(keyword: String) {
        validKeyword.value = keyword
    }

    fun clearInputKeyword() {
        inputKeyword.value = ""
    }

    fun updateHeartState(content: Content, isSelected: Boolean) {
        if (isSelected)
            localStorage.addArchivedContent(content)
        else localStorage.deleteArchivedContent(content)

        _archivedContents.value = localStorage.contents
    }
}
