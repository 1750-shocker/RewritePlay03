package com.gta.rewriteplay03.project.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gta.core.view.base.BaseFragment
import com.gta.model.pojo.QueryArticle
import com.gta.rewriteplay03.R
import com.gta.rewriteplay03.article.ArticleAdapter
import com.gta.rewriteplay03.databinding.FragmentProjectListBinding

private const val PROJECT_CID = "PROJECT_CID"
open class ProjectListFragment: BaseFragment<ProjectListViewModel, FragmentProjectListBinding>() {
    override val viewModel: ProjectListViewModel by viewModels()
    override val layoutId = R.layout.fragment_project_list
    private lateinit var articleAdapter: ArticleAdapter
    private var page = 1
    private var projectCid: Int? = null
    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        return dataBinding.root
    }

    override fun initData() {
//        setDataStatus(viewModel.dataLiveData, {
//            if (viewModel.dataList.size > 0) loadFinished()
//        }) {
//            if (page == 1 && viewModel.dataList.size > 0) {
//                viewModel.dataList.clear()
//            }
//            viewModel.dataList.addAll(it)
//            articleAdapter.notifyItemInserted(it.size)
//        }
//        getArticleList(false)
    }

    override fun initView() {
//        articleAdapter = ArticleAdapter(requireContext(), viewModel.dataList)
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }
    private fun refreshData(){
        getArticleList(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectCid = it.getInt(PROJECT_CID)
        }
    }
    private fun getArticleList(isRefresh: Boolean) {
//        if (viewModel.dataList.size <= 0) {
//            startLoading()
//            projectCid?.apply {
//                viewModel.getDataList(QueryArticle(page, this, isRefresh))
//            }
//        }
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