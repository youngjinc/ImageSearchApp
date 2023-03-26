package com.kakaobank.imgsurfer.data.model

import com.kakaobank.imgsurfer.data.util.toLocalDatetimeForDTO
import com.kakaobank.imgsurfer.domain.model.Content
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoSearchDTO(
    @SerialName("thumbnail")
    val thumbnailUrl: String,
    val datetime: String,
    val author: String,
) {
    fun toContentList() = Content(thumbnailUrl = thumbnailUrl,
        dateTime = datetime.toLocalDatetimeForDTO(),
        source = author)
}
