package com.gta.rewriteplay03.project

import android.annotation.SuppressLint
import android.content.pm.ServiceInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gta.core.util.getStatusBarHeight
import com.gta.core.view.base.BaseFragment
import com.gta.core.view.custom.FragmentAdapter
import com.gta.core.view.custom.MediaSourceDialog
import com.gta.model.model.ServiceBean
import com.gta.rewriteplay03.R
import com.gta.rewriteplay03.databinding.FragmentProjectBinding
import com.gta.rewriteplay03.project.list.ProjectListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectFragment() : BaseFragment<ProjectViewModel, FragmentProjectBinding>(), TabLayout.OnTabSelectedListener {
    override val viewModel: ProjectViewModel by viewModels()
    override val layoutId = R.layout.fragment_project
    private lateinit var adapter: FragmentAdapter
    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
    }


    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}