package com.kakaobank.imgsurfer.data.service

import com.kakaobank.imgsurfer.data.model.ResponseBase
import com.kakaobank.imgsurfer.data.model.ResponseImageSearch
import com.kakaobank.imgsurfer.data.model.ResponseVideoSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/image")
    suspend fun searchImage(
        @Query("query") keyword: String,
        @Query("sort") sort: String = SORT_TYPE_RECENCY,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): ResponseBase<ResponseImageSearch>

    @GET("search/vclip")
    suspend fun searchVideo(
        @Query("query") keyword: String,
        @Query("sort") sort: String = SORT_TYPE_RECENCY,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): ResponseBase<ResponseVideoSearch>

    companion object {
        private const val SORT_TYPE_RECENCY = "recency"
    }
}
