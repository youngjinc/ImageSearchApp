package com.kakaobank.imagesearch.presentation

import android.os.Bundle
import com.kakaobank.imagesearch.R
import com.kakaobank.imagesearch.databinding.ActivityMainBinding
import com.kakaobank.imagesearch.presentation.archive.ArchiveFragment
import com.kakaobank.imagesearch.presentation.search.SearchFragment
import com.kakaobank.imagesearch.util.binding.BindingActivity
import com.kakaobank.imagesearch.util.extension.KakaoLog
import com.kakaobank.imagesearch.util.extension.replace

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeFragment(R.id.search)
        addListeners()
    }

    private fun addListeners() {
        binding.bnvMain.setOnItemSelectedListener { menu ->
            changeFragment(menu.itemId)
            true
        }
    }

    private fun changeFragment(menuId: Int) {
        when (menuId) {
            R.id.search -> replace<SearchFragment>(R.id.fcv_main)
            R.id.archive -> replace<ArchiveFragment>(R.id.fcv_main)
            else -> KakaoLog.e(throwable = IllegalArgumentException("Not found menu item id"))
        }
    }
}