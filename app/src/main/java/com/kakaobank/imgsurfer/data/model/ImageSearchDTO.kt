package com.kakaobank.imgsurfer.data.model

import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.util.extension.toLocalDatetimeForDTO
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
