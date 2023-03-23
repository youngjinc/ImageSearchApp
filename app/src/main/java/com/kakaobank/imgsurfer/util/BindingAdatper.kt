package com.kakaobank.imgsurfer.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
fun TextView.setDateTime(localDateTime: LocalDateTime) {
    text = localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.KOREA))
}
