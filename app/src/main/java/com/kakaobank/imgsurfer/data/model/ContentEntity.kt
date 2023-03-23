package com.kakaobank.imgsurfer.data.model

import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.util.extension.toLocalDatetimeForEntity
import kotlinx.serialization.Serializable

@Serializable
data class ContentEntity(
    val imageUrl: String,
    val dateTime: String,
    val source: String,
) {
    fun toContent() = Content(imageUrl, dateTime.toLocalDatetimeForEntity(), source)
}
