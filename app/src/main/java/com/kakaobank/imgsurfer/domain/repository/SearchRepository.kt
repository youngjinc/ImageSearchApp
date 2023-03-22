package com.kakaobank.imgsurfer.domain.repository

import androidx.paging.PagingData
import com.kakaobank.imgsurfer.domain.model.Content
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchContent(keyword: String): Flow<PagingData<Content>>
}
