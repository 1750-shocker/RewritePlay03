package com.gta.rewriteplay03.article

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.gta.core.view.base.BaseRecyclerAdapter
import com.gta.model.room.entity.Article
import com.gta.rewriteplay03.databinding.AdapterArticleBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class ArticleAdapter(
    private val mContext: Context,
    private val articleList: ArrayList<Article>,
    private val isShowCollect: Boolean = true
) : BaseRecyclerAdapter<AdapterArticleBinding>(), CoroutineScope by MainScope() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerHolder<AdapterArticleBinding> {
        val binding =
            AdapterArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseRecyclerHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBaseBindViewHolder(position: Int, binding: AdapterArticleBinding) {
        val data = articleList[position]
        binding.apply {

        }
    }


}