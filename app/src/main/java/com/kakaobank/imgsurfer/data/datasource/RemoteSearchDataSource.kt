package com.kakaobank.imgsurfer.data.datasource

import com.kakaobank.imgsurfer.data.service.SearchService
import javax.inject.Inject

class RemoteSearchDataSource @Inject constructor(
    private val searchService: SearchService,
)
