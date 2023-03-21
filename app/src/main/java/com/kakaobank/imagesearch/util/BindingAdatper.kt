package com.kakaobank.imagesearch.util

import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("radius")
fun ShapeableImageView.setCornerRadius(radius: Float) {
    val shapeAppearanceModel = this.shapeAppearanceModel.toBuilder()
        .setAllCornerSizes(radius)
        .build()
    this.shapeAppearanceModel = shapeAppearanceModel
}
