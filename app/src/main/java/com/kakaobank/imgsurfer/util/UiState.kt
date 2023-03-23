package com.kakaobank.imgsurfer.util

sealed class UiState<out T> { // TODO 삭제 고려
    object Init : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
}
