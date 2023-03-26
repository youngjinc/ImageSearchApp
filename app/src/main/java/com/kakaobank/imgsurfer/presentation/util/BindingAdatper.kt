package com.kakaobank.imgsurfer.presentation.util

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
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

@BindingAdapter("layoutMarginTop")
fun setLayoutMarginTop(view: View, dimen: Float) {
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
        topMargin = dimen.toInt()
    }
}

@BindingAdapter("dateTime")
fun TextView.setDateTime(localDateTime: LocalDateTime) {
    text = localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.KOREA))
}
