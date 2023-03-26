package com.kakaobank.imgsurfer.presentation.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.kakaobank.imgsurfer.R
import com.kakaobank.imgsurfer.databinding.FragmentArchiveBinding
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.presentation.ContentViewModel
import com.kakaobank.imgsurfer.presentation.adapter.ArchivedContentAdapter
import com.kakaobank.imgsurfer.presentation.screen.ContentDetailActivity.Companion.ARG_IMAGE_URL
import com.kakaobank.imgsurfer.presentation.screen.ContentDetailActivity.Companion.ARG_TITLE
import com.kakaobank.imgsurfer.presentation.util.binding.BindingFragment
import com.kakaobank.imgsurfer.presentation.util.extension.collectFlow

class ArchiveFragment : BindingFragment<FragmentArchiveBinding>(R.layout.fragment_archive) {
    private val viewModel: ContentViewModel by activityViewModels()
    private val adapter = ArchivedContentAdapter(::updateHeartState, ::moveToDetail)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initLayout()
        collectData()
    }

    private fun initLayout() {
        binding.rvArchiveContents.adapter = adapter
    }

    private fun collectData() {
        collectFlow(viewModel.archivedContents) {
            adapter.submitList(it.toMutableList())
        }
    }

    private fun updateHeartState(content: Content?, isSelected: Boolean) {
        if (content == null) return
        viewModel.updateHeartState(content, isSelected)
    }

    private fun moveToDetail(content: Content) {
        Intent(requireContext(), ContentDetailActivity::class.java).apply {
            putExtra(ARG_TITLE, content.source)
            putExtra(ARG_IMAGE_URL,
                content.imageUrl
                    ?: content.thumbnailUrl) // 동영상은 원본 이미지 url을 받아올 수 없기 때문에 디테일뷰에서 thumbnailUrl을 보여줌.
        }.also {
            startActivity(it)
        }
    }
}
