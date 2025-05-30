package com.gta.rewriteplay03.mediaActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gta.core.view.base.BaseActivity
import com.gta.core.view.custom.FragmentAdapter
import com.gta.core.view.custom.LoginDialogFragment
import com.gta.core.view.custom.MediaSourceDialog
import com.gta.model.model.ServiceBean
import com.gta.rewriteplay03.R
import com.gta.rewriteplay03.databinding.ActivityMediaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaActivity : BaseActivity<MediaViewModel, ActivityMediaBinding>(),
    com.google.android.material.tabs.TabLayout.OnTabSelectedListener {

    override val viewModel: MediaViewModel by viewModels()
    override val layoutId: Int = R.layout.activity_media
    private lateinit var adapter: FragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TODO:(3/14/23 这里要调用viewModel的方法获取tab数据)
        dataBinding.serviceInfo = ServiceBean(
            "网易云音乐",
            ContextCompat.getDrawable(this@MediaActivity, R.drawable.ic_project)
        )
        dataBinding.mediaSourceBar.setOnClickListener {
            MediaSourceDialog(this).showDialog(this)
        }
        adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        dataBinding.apply {
            vp2.adapter = adapter
            mainTab.addOnTabSelectedListener(this@MediaActivity)
            TabLayoutMediator(mainTab, vp2) { tab, position ->
                tab.text = adapter.title(position)
            }.attach()
//            mainTab.setPadding(0, getStatusBarHeight(), 0, 0)
        }
        setDataStatus(viewModel.dataLiveData) {
            val nameList = mutableListOf<String>()
            val viewList = mutableListOf<Fragment>()
            //数据没错
            it.forEach { projectTab ->
                nameList.add(projectTab.name)
                viewList.add(TemplateFragment.getTemplateFragment(projectTab.id))
            }
            adapter.apply {
                reset(nameList.toTypedArray())
                reset(viewList)
                notifyItemRangeChanged(0, nameList.size)
            }
            dataBinding.vp2.currentItem = viewModel.position
        }
    }

    companion object {
        private const val TAG = "MediaActivity"
        fun actionStart(context: Context) {
            val intent = Intent(context, MediaActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
        if (tab != null && tab.position > 0)
            viewModel.position = tab.position
    }

    override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}

    override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}

}