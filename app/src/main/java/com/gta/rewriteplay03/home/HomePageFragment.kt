package com.gta.rewriteplay03.home

import androidx.fragment.app.Fragment
import com.gta.core.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment:Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = HomePageFragment()
    }
}