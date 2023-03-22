package com.kakaobank.imgsurfer.data.model

import com.kakaobank.imgsurfer.domain.model.Content
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseImageSearch(
    val documents: List<ImageInfo>,
) {
    @Serializable
    data class ImageInfo(
        @SerialName("thumbnail_url")
        val thumbnailUrl: String,
        val datetime: String,
        @SerialName("display_sitename")
        val source: String,
    ) {
        fun toContent() = Content(thumbnailUrl, datetime, source)
    }
}
