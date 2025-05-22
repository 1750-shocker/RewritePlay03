package com.gta.rewriteplay03.project

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gta.core.util.getStatusBarHeight
import com.gta.core.view.base.BaseFragment
import com.gta.core.view.custom.FragmentAdapter
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

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        return dataBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {
        startLoading()
        setDataStatus(viewModel.dataLiveData) {
            val nameList = mutableListOf<String>()
            val viewList = mutableListOf<Fragment>()
            //获取项目列表数据，并通过project.id去新建对应的ListFragment
            it.forEach { project ->
                nameList.add(project.name)
                viewList.add(ProjectListFragment.newInstance(project.id))
            }
            //将数据源交给适配器
            adapter.apply {
                reset(nameList.toTypedArray())
                reset(viewList)
                notifyDataSetChanged()
            }
            dataBinding.projectViewPager2.currentItem = viewModel.position
        }
    }

    override fun initView() {
        adapter = FragmentAdapter(requireActivity().supportFragmentManager, lifecycle)
        dataBinding.apply{
            projectViewPager2.adapter = adapter
            projectTabLayout.addOnTabSelectedListener(this@ProjectFragment)
            TabLayoutMediator(projectTabLayout, projectViewPager2){tab, position->
                tab.text = adapter.title(position)
            }.attach()
            mediaSourceBar.setPadding(0, context.getStatusBarHeight(), 0, 0)
            mediaSourceBar.setOnClickListener{
                //TODO：切换播放源
            }
        }
        //由于我们在这个页面添加了MediaSourceBar，我们要给他数据，就要在这里把数据源设置到databinding中
//        TODO：dataBinding.serviceInfo = xxx,其中serviceInfo是databinding中的一个变量
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null && tab.position > 0)
            viewModel.position = tab.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}