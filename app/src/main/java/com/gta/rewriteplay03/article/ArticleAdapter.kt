package com.gta.rewriteplay03.article

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gta.core.view.base.BaseRecyclerAdapter
import com.gta.model.room.entity.Article
import com.gta.rewriteplay03.databinding.AdapterArticleBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class ArticleAdapter(
    private val mContext: Context,
    val articleList: ArrayList<Article>,
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
            if (!TextUtils.isEmpty(data.title)) {
                articleTitle.text = data.title
            }
            articleAuthor.text = if (TextUtils.isEmpty(data.author)) data.shareUser else data.author
            articleTime.text = data.niceShareDate
            if (!TextUtils.isEmpty(data.envelopePic)) {
                Glide.with(mContext).load(data.envelopePic).into(articleImage)
            }
            root.setOnClickListener {
                //TODO:跳转到文章详情页面
                Toast.makeText(mContext, "点击了第${position + 1}条数据", Toast.LENGTH_SHORT).show()
            }
        }
    }


}