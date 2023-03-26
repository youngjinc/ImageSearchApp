package com.kakaobank.imgsurfer.presentation.screen

import android.os.Bundle
import coil.load
import com.kakaobank.imgsurfer.R
import com.kakaobank.imgsurfer.databinding.ActivityContentDetailBinding
import com.kakaobank.imgsurfer.presentation.util.binding.BindingActivity

class ContentDetailActivity :
    BindingActivity<ActivityContentDetailBinding>(R.layout.activity_content_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        with(intent) {
            getStringExtra(ARG_TITLE)?.let { binding.tvTitle.text = it }
            getStringExtra(ARG_IMAGE_URL)?.let { binding.ivContent.load(it) }
        }
    }

    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val ARG_TITLE = "title"
        const val ARG_IMAGE_URL = "image"
    }
}
