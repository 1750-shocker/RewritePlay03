package com.gta.rewriteplay03.project

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectFragment: Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()
    }
}