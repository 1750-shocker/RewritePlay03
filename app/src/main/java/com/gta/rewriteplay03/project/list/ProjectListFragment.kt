package com.gta.rewriteplay03.project.list

import android.os.Bundle
import androidx.fragment.app.Fragment

private const val PROJECT_CID = "PROJECT_CID"
class ProjectListFragment: Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(cid: Int) =
            ProjectListFragment().apply {
                arguments = Bundle().apply {
                    putInt(PROJECT_CID, cid)
                }
            }
    }
}