package com.kakaobank.imgsurfer.data.model

import com.kakaobank.imgsurfer.data.util.toLocalDatetimeForDTO
import com.kakaobank.imgsurfer.domain.model.Content
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageSearchDTO(
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("image_url")
    val imageUrl: String,
    val datetime: String,
    @SerialName("display_sitename")
    val source: String,
) {
    fun toContentList() = Content(thumbnailUrl, imageUrl, datetime.toLocalDatetimeForDTO(), source)
}
