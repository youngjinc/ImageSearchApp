package com.kakaobank.imgsurfer.domain.model

import java.time.LocalDateTime

data class Content(
    val imageUrl: String,
    val dateTime: LocalDateTime?,
    val source: String,
)
