package com.kakaobank.imgsurfer.presentation.screen

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kakaobank.imgsurfer.R
import com.kakaobank.imgsurfer.databinding.ActivityMainBinding
import com.kakaobank.imgsurfer.presentation.util.binding.BindingActivity
import com.kakaobank.imgsurfer.presentation.util.extension.replace
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
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
            else -> {}
        }
    }
}
