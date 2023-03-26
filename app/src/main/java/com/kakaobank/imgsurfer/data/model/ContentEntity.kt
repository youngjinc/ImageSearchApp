package com.kakaobank.imgsurfer.data.model

import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.data.util.toLocalDatetimeForEntity
import kotlinx.serialization.Serializable

@Serializable
data class ContentEntity(
    val thumbnailUrl: String,
    val imageUrl: String?,
    val dateTime: String,
    val source: String,
) {
    fun toContent() = Content(thumbnailUrl, imageUrl, dateTime.toLocalDatetimeForEntity(), source)
}
