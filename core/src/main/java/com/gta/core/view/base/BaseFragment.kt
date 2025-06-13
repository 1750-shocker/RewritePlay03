package com.gta.core.view.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gta.core.R
import com.gta.core.util.showToast
import com.gta.core.view.base.lce.DefaultLceImpl
import com.gta.core.view.base.lce.ILce
import com.scwang.smart.refresh.layout.util.SmartUtil.dp2px
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


abstract class BaseFragment : Fragment(), ILce, BaseFragmentInit {

    /**
     * Fragment中由于服务器异常导致加载失败显示的布局。
     */
    private var loadErrorView: View? = null

    /**
     * Fragment中由于网络异常导致加载失败显示的布局。
     */
    private var badNetworkView: View? = null

    /**
     * Fragment中当界面上没有任何内容时展示的布局。
     */
    private var noContentView: View? = null


    /**
     * Fragment中显示加载等待的控件。
     */
    private var loading: View? = null

    private var defaultLce: ILce? = null

    protected open fun isHaveHeadMargin(): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //这里是将原本的Fragment的ui和lce视图叠放在一起，所以用FrameLayout做基底
        val frameLayout = FrameLayout(requireContext())
        val lce = View.inflate(context, R.layout.layout_lce, null)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        val isPort = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        params.setMargins(
            0,
            if (isHaveHeadMargin()) {
                dp2px(if (isPort) 70f else 55f)
            } else 0,
            0,
            0
        )
        lce.layoutParams = params
        //这个是原本的Fragment的ui
        val content = getLayoutView(inflater, container, false)
        //这和Activity的实现有不同,Activity可以直接调用AppCompatActivity的addContentView()
        frameLayout.addView(content)
        //将lce视图叠放在原本的视图上面
        frameLayout.addView(lce)
        //两段逻辑分开，方便看
        onCreateView(lce)
        return frameLayout
    }
    /**
     * 在Fragment基类中获取通用的控件，会将传入的View实例原封不动返回。
     * @param view
     * Fragment中inflate出来的View实例。
     * @return  Fragment中inflate出来的View实例原封不动返回。
     */
    private fun onCreateView(view: View): View {
        loading = view.findViewById(R.id.loading)
        noContentView = view.findViewById(R.id.noContentView)
        badNetworkView = view.findViewById(R.id.badNetworkView)
        loadErrorView = view.findViewById(R.id.loadErrorView)
        //activity那里是打印日志
        if (loading == null) {
            throw NullPointerException("loading is null")
        }
        if (badNetworkView == null) {
            throw NullPointerException("badNetworkView is null")
        }
        if (loadErrorView == null) {
            throw NullPointerException("loadErrorView is null")
        }
        defaultLce = DefaultLceImpl(
            loading,
            loadErrorView,
            badNetworkView,
            noContentView
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //注意ui和数据初始化时期
        initView()
        initData()
    }

    fun <T> setDataStatusFlow(
        flow: Flow<Result<T>>,
        onBadNetwork: () -> Unit = {},
        onDataStatus: (T) -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            flow.collect { result ->
                if (result.isSuccess) {
                    val data = result.getOrNull()
                    if (data != null) {
                        loadFinished()
                        onDataStatus(data)
                    } else {
                        showLoadErrorView()
                    }
                } else {
                    context?.showToast(getString(R.string.bad_network_view_tip))
                    showBadNetworkView { initData() }
                    //比activity多一个网络问题的处理逻辑，并且是自动触发的
                    onBadNetwork.invoke()
                }
            }

        }
    }



    @CallSuper
    override fun startLoading() {
        defaultLce?.startLoading()
    }

    @CallSuper
    override fun loadFinished() {
        defaultLce?.loadFinished()
    }

    override fun showLoadErrorView(tip: String) {
        defaultLce?.showLoadErrorView(tip)
    }

    override fun showBadNetworkView(listener: View.OnClickListener) {
        defaultLce?.showBadNetworkView(listener)
    }

    override fun showNoContentView(tip: String) {
        defaultLce?.showNoContentView(tip)
    }

}

