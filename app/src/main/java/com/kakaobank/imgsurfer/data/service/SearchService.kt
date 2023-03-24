package com.kakaobank.imgsurfer.data.service

import com.kakaobank.imgsurfer.data.model.ImageSearchDTO
import com.kakaobank.imgsurfer.data.model.ResponseBase
import com.kakaobank.imgsurfer.data.model.VideoSearchDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    /** [Request]
     * @param sort : 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
     * @param page : 결과 페이지 번호, 1~15 사이의 값
     * @param size : 한 페이지에 보여질 문서 수, 1~30 사이의 값, 기본 값 15
     * */
    @GET("search/image")
    suspend fun searchImage(
        @Query("query") keyword: String,
        @Query("sort") sort: String = SORT_TYPE_RECENCY,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): ResponseBase<ImageSearchDTO>

    @GET("search/vclip")
    suspend fun searchVideo(
        @Query("query") keyword: String,
        @Query("sort") sort: String = SORT_TYPE_RECENCY,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): ResponseBase<VideoSearchDTO>

    companion object {
        private const val SORT_TYPE_RECENCY = "recency"
    }
}
