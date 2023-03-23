package com.kakaobank.imgsurfer.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.kakaobank.imgsurfer.util.extension.KakaoLog
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle
import java.util.*

@BindingAdapter("image")
fun ImageView.setImage(imageUrl: String) {
    load(imageUrl)
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("dateTime")
fun TextView.setDateTime(dateTime: String) {
    try { // TODO 도메인 레이어에서 변환하기
        val localDateTime = LocalDateTime.from(
            Instant.from(
                DateTimeFormatter.ISO_DATE_TIME.parse(dateTime)
            ).atZone(ZoneId.of("Asia/Seoul"))
        )
        text = localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withLocale(Locale.KOREA)) // TODO 언어 변경 테스트해보기
    } catch (e: DateTimeParseException) {
        KakaoLog.e(e.message ?: return)
    }
}
