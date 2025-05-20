package com.gta.rewriteplay03.profile

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}