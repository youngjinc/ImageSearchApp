package com.kakaobank.imgsurfer.domain.model

import java.time.LocalDateTime

data class Content(
    val thumbnailUrl: String,
    val imageUrl: String? = null,
    val dateTime: LocalDateTime?,
    val source: String,
)
