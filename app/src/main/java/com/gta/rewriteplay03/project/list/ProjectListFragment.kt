package com.gta.rewriteplay03.project.list

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.gta.model.pojo.QueryArticle
import com.gta.rewriteplay03.article.ArticleAdapter
import com.gta.rewriteplay03.base.BaseListFragment
import dagger.hilt.android.AndroidEntryPoint


private const val PROJECT_CID = "PROJECT_CID"

@AndroidEntryPoint
class ProjectListFragment : BaseListFragment() {

    private val viewModel by viewModels<ProjectListViewModel>()

    private var projectCid: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectCid = it.getInt(PROJECT_CID)
        }
    }

    override fun refreshData() {
        getArticleList(true)
    }

    override fun isHaveHeadMargin(): Boolean {
        return false
    }

    override fun initView() {
        articleAdapter = ArticleAdapter(requireContext(), viewModel.dataList)
        super.initView()
    }

    private fun getArticleList(isRefresh: Boolean) {
        if (viewModel.dataList.size <= 0) {
            startLoading()
            projectCid?.apply {
                viewModel.getDataList(QueryArticle(page, this, isRefresh))
            }
        }
    }

    override fun initData() {
        setDataStatus(viewModel.dataLiveData, {
            if (viewModel.dataList.isNotEmpty()) loadFinished()
        }) {
            if (page == 1 && viewModel.dataList.isNotEmpty()) {
                viewModel.dataList.clear()
            }
            viewModel.dataList.addAll(it)
            articleAdapter.notifyItemInserted(it.size)
        }
        getArticleList(false)
    }

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