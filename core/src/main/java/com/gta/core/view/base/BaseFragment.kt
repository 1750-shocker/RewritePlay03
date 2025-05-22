package com.gta.core.view.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gta.core.R
import com.gta.core.util.showToast
import com.gta.core.view.base.lce.DefaultLceImpl
import com.gta.core.view.base.lce.ILce
import com.scwang.smart.refresh.layout.util.SmartUtil.dp2px


abstract class BaseFragment<T: ViewModel, V: ViewDataBinding> : Fragment(), ILce, BaseFragmentInit {
    abstract val viewModel: T
    abstract val layoutId: Int
    lateinit var dataBinding: V
    /**
     * 加载数据的LiveData。
     */

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
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
        //这里是将原本的Fragment的ui和lce视图叠放在一起，所以用FrameLayout做基底
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

    //和activity那里一样，多一个网络失败的主动处理
    fun <T> setDataStatus(
        dataLiveData: LiveData<Result<T>>,
        onBadNetwork: () -> Unit = {},
        onDataStatus: (T) -> Unit
    ) {
        dataLiveData.observe(this) {
            if (it.isSuccess) {
                val dataList = it.getOrNull()
                if (dataList != null) {
                    loadFinished()
                    onDataStatus(dataList)
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

