package com.kakaobank.imgsurfer.util

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("image")
fun ImageView.setImage(imageUrl: String) {
    this.load(imageUrl)
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("radius")
fun ShapeableImageView.setCornerRadius(radius: Float) {
    val shapeAppearanceModel = this.shapeAppearanceModel.toBuilder()
        .setAllCornerSizes(radius)
        .build()
    this.shapeAppearanceModel = shapeAppearanceModel
}
