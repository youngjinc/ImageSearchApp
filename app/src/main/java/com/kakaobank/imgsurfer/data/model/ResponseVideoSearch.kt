package com.kakaobank.imgsurfer.data.model

import com.kakaobank.imgsurfer.domain.model.Content
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseVideoSearch(
    @SerialName("thumbnail")
    val thumbnailUrl: String,
    val datetime: String,
    val author: String,
) {
    fun toContentList() = Content(thumbnailUrl, datetime, author)
}
