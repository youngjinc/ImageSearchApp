package com.kakaobank.imgsurfer.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.kakaobank.imgsurfer.R
import com.kakaobank.imgsurfer.databinding.FragmentArchiveBinding
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.presentation.SearchViewModel
import com.kakaobank.imgsurfer.presentation.adapter.ArchivedContentAdapter
import com.kakaobank.imgsurfer.util.binding.BindingFragment
import com.kakaobank.imgsurfer.util.extension.collectFlow

class ArchiveFragment : BindingFragment<FragmentArchiveBinding>(R.layout.fragment_archive) {
    private val viewModel: SearchViewModel by activityViewModels()
    private val adapter = ArchivedContentAdapter(::updateHeartState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}
