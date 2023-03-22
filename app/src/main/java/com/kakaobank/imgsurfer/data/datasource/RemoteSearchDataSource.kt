package com.kakaobank.imgsurfer.data.datasource

import com.kakaobank.imgsurfer.data.model.ResponseImageSearch
import com.kakaobank.imgsurfer.data.service.SearchService
import javax.inject.Inject

class RemoteSearchDataSource @Inject constructor(
    private val searchService: SearchService,
) {
    suspend fun searchImage(keyword: String): ResponseImageSearch =
        searchService.searchImage(keyword)
}
