package com.gta.rewriteplay03.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gta.core.view.custom.StaggeredDividerItemDecoration
import com.gta.rewriteplay03.databinding.LayoutToTopBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlin.system.measureTimeMillis


class ToTopRecyclerView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(mContext, attrs, defStyleAttr), View.OnClickListener {

    private lateinit var mToTopSmartRefreshLayout: SmartRefreshLayout
    private lateinit var mToTopRecycleView: RecyclerView
    private lateinit var mToTopIvClick: ImageView
    private val mLoadTime = 1000

    /**
     * 初始化布局
     */
    private fun initView() {
        //加载布局
        val binding = LayoutToTopBinding.inflate(LayoutInflater.from(context), this, true)
        binding.apply {
            mToTopSmartRefreshLayout = toTopSmartRefreshLayout
            mToTopRecycleView = toTopRecycleView
            mToTopIvClick = toTopIvClick
            mToTopIvClick.setOnClickListener(this@ToTopRecyclerView)
            mToTopRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!recyclerView.canScrollVertically(-1)) {
                        mToTopIvClick.visibility = View.GONE
                    } else if (dy < 0) {
                        mToTopIvClick.visibility = View.VISIBLE
                    } else if (dy > 0) {
                        mToTopIvClick.visibility = View.GONE
                    }
                }
            })
        }
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        adapter.setHasStableIds(true)
        mToTopRecycleView.adapter = adapter
    }

    fun onRefreshListener(onRefreshListener: () -> Unit, onLoadMoreListener: () -> Unit) {
        mToTopSmartRefreshLayout.apply {
            setOnRefreshListener { reLayout ->
                reLayout.finishRefresh(measureTimeMillis {
                    onRefreshListener.invoke()
                }.toInt())
            }
            setOnLoadMoreListener { reLayout ->
                val time = measureTimeMillis {
                    onLoadMoreListener.invoke()
                }.toInt()
                reLayout.finishLoadMore(if (time > mLoadTime) time else mLoadTime)
            }
        }
    }

    fun setRecyclerViewLayoutManager(isLinearLayout: Boolean) {
        if (isLinearLayout) {
            mToTopRecycleView.layoutManager = LinearLayoutManager(context)
        } else {
            val spanCount = 2
            val layoutManager =
                StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            mToTopRecycleView.layoutManager = layoutManager
            layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            mToTopRecycleView.itemAnimator = null
            mToTopRecycleView.addItemDecoration(
                StaggeredDividerItemDecoration(
                    context
                )
            )
            mToTopRecycleView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int
                ) {
                    val first = IntArray(spanCount)
                    layoutManager.findFirstCompletelyVisibleItemPositions(first)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                        layoutManager.invalidateSpanAssignments()
                    }
                }
            })
        }
    }


    init {
        initView()
    }

    override fun onClick(v: View) {
        mToTopRecycleView.smoothScrollToPosition(0)
    }
}