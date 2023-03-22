package com.kakaobank.imgsurfer.data.service

import com.kakaobank.imgsurfer.data.model.ResponseImageSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/image")
    suspend fun searchImage(@Query("query") keyword: String): ResponseImageSearch
}
