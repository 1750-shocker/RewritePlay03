package com.gta.rewriteplay03.base

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gta.rewriteplay03.article.ArticleAdapter
import com.gta.rewriteplay03.databinding.FragmentBaseListBinding
import com.gta.rewriteplay03.home.ArticleCollectBaseFragment

abstract class BaseListFragment : ArticleCollectBaseFragment() {

    protected var binding: FragmentBaseListBinding? = null

    protected lateinit var articleAdapter: ArticleAdapter
    protected var page = 1

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        binding = FragmentBaseListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun initView() {
        binding?.apply {
            baseFragmentToTop.setRecyclerViewLayoutManager(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            baseFragmentToTop.setAdapter(articleAdapter)
            baseFragmentToTop.onRefreshListener({
                page = 1
                refreshData()
            }, {
                page++
                refreshData()
            })
        }
    }

}