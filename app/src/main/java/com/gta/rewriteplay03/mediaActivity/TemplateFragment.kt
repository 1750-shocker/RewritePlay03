package com.gta.rewriteplay03.mediaActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.gta.core.view.base.BaseFragment
import com.gta.model.pojo.QueryArticle
import com.gta.rewriteplay03.R
import com.gta.rewriteplay03.article.ArticleAdapter
import com.gta.rewriteplay03.databinding.TemplateFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplateFragment private constructor() :
    BaseFragment<TemplateViewModel, TemplateFragmentBinding>() {
    override val viewModel: TemplateViewModel by viewModels()

    override val layoutId: Int = R.layout.template_fragment
    private var projectCid: Int? = null
    private var page=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 3/14/11 这里读取bundle，然后根据bundle的内容来调用viewmodel的方法获取展示数据
        arguments?.let {
            projectCid = it.getInt("PROJECT_CID")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArticleAdapter(requireContext(), viewModel.dataList)
        dataBinding.recyclerview.adapter = adapter
        dataBinding.recyclerview.layoutManager = GridLayoutManager(context,4)
        setDataStatus(viewModel.dataLiveData){
            //数据请求正常后进行填充
            if (page == 1 && viewModel.dataList.size > 0) {
                viewModel.dataList.clear()
            }
            viewModel.dataList.addAll(it)
            Toast.makeText(context, "数据填充成功", Toast.LENGTH_SHORT).show()
            adapter.notifyItemInserted(it.size)
        }
    }



    private fun getArticleList(isRefresh: Boolean) {
        if (viewModel.dataList.size <= 0) {
            projectCid?.apply {
                //参数有可能为空
                viewModel.getDataList(QueryArticle(page, this, isRefresh))
            }
        }
    }

    companion object {
        const val MEDIA_ITEM_MEDIA_ID = "MEDIA_ITEM_MEDIA_ID"
        const val MEDIA_ITEM_TITLE = "MEDIA_ITEM_TITLE"

        // 3/13/23 通过给定的mediaItem，传入bundle来创建fragment
        fun getTemplateFragment(cid: Int) = TemplateFragment().apply {
            val extras = Bundle().apply {
                putInt("PROJECT_CID", cid)
            }
        }
    }
}