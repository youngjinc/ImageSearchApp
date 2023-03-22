package com.kakaobank.imgsurfer.data.model

import com.kakaobank.imgsurfer.domain.model.Content
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseImageSearch(
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    val datetime: String,
    @SerialName("display_sitename")
    val source: String,
) {
    fun toContentList() = Content(thumbnailUrl, datetime, source)
}
