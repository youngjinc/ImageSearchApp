package com.kakaobank.imgsurfer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kakaobank.imgsurfer.data.datasource.LocalDataSource
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.domain.repository.SearchRepository
import com.kakaobank.imgsurfer.presentation.type.UiStateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val localStorage: LocalDataSource,
) : ViewModel() {
    val inputKeyword = MutableStateFlow("")
    private val validKeyword = MutableStateFlow("")
    val searchState = MutableStateFlow(UiStateType.INIT)
    val hasFocusingSearchBar = MutableStateFlow(false)

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

    /** 검색 키워드가 지워진 상태인 경우, 검색바 외부영역을 터치했을 때 검색바에 키워드 다시 보여주기 */
    fun reloadInputKeyword() {
        if (inputKeyword.value != validKeyword.value && validKeyword.value.isNotEmpty())
            inputKeyword.value = validKeyword.value
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

    fun isArchivedContent(content: Content?) = localStorage.isArchivedContent(content)
}
