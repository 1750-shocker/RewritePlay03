package com.gta.rewriteplay03.home

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.View
import com.gta.core.view.base.BaseActivity
import com.gta.core.view.base.BaseFragment
import com.gta.rewriteplay03.article.ArticleBroadCast



abstract class ArticleCollectBaseFragment : BaseFragment() {

    private var articleReceiver: BroadcastReceiver? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleReceiver =
            ArticleBroadCast.setArticleChangesReceiver(requireActivity()) { refreshData() }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    abstract fun refreshData()

    override fun onDestroyView() {
        super.onDestroyView()
        ArticleBroadCast.clearArticleChangesReceiver(requireActivity(), articleReceiver)
    }

}