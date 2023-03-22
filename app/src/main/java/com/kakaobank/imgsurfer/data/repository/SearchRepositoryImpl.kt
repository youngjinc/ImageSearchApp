package com.kakaobank.imgsurfer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kakaobank.imgsurfer.data.datasource.SearchPagingSource
import com.kakaobank.imgsurfer.data.service.SearchService
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
) : SearchRepository {
    override fun searchContent(keyword: String): Flow<PagingData<Content>> =
        Pager(PagingConfig(pageSize = 10)) {
            SearchPagingSource(searchService, keyword)
        }.flow
}
