package com.kakaobank.imgsurfer.util.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <T, R> R.collectFlow(
    flow: StateFlow<T>, crossinline block: suspend (T) -> Unit
) {
    when (this) {
        is AppCompatActivity -> flow.flowWithLifecycle(lifecycle).onEach { block(it) }
            .launchIn(lifecycleScope)

        is Fragment -> flow.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { block(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        else -> {}
    }
}
