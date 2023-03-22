package com.kakaobank.imgsurfer.domain.repository

import com.kakaobank.imgsurfer.domain.model.Content

interface SearchRepository {
    suspend fun searchImage(keyword: String): Result<List<Content>>
}
