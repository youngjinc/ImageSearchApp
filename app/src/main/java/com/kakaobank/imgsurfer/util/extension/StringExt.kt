package com.kakaobank.imgsurfer.util.extension

import com.kakaobank.imgsurfer.util.KakaoLog
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun String.toLocalDatetimeForDTO() = try {
    LocalDateTime.from(
        Instant.from(
            DateTimeFormatter.ISO_DATE_TIME.parse(this)
        ).atZone(ZoneId.of("Asia/Seoul"))
    )
} catch (e: DateTimeParseException) {
    e.message?.let { KakaoLog.e(it) }
    null
}

fun String.toLocalDatetimeForEntity() = try {
    LocalDateTime.parse(this)
} catch (e: DateTimeParseException) {
    e.message?.let { KakaoLog.e(it) }
    null
}
