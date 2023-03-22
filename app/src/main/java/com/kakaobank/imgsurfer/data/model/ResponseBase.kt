package com.kakaobank.imgsurfer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBase<T>(
    val meta: Meta,
    val documents: List<T>,
) {
    @Serializable
    data class Meta(
        @SerialName("is_end")
        val isEnd: Boolean,
    )
}
