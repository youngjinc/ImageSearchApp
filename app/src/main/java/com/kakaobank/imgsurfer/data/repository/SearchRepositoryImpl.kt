package com.kakaobank.imgsurfer.data.repository

import com.kakaobank.imgsurfer.data.datasource.RemoteSearchDataSource
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchDataSource: RemoteSearchDataSource) :
    SearchRepository {
    override suspend fun searchImage(keyword: String): Result<List<Content>> =
        runCatching {
            searchDataSource.searchImage(keyword).documents.map { it.toContent() }
        }
}
