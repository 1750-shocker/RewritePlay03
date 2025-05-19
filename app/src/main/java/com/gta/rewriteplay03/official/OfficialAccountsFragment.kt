package com.gta.rewriteplay03.official

import androidx.fragment.app.Fragment
import com.gta.core.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfficialAccountsFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = OfficialAccountsFragment()
    }
}