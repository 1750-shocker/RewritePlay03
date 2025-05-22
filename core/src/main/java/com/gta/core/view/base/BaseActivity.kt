package com.gta.core.view.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.gta.core.R
import com.gta.core.util.AndroidVersion
import com.gta.core.util.showToast
import com.gta.core.util.transparentStatusBar
import com.gta.core.view.base.lce.DefaultLceImpl
import com.gta.core.view.base.lce.ILce
import com.scwang.smart.refresh.layout.util.SmartUtil.dp2px
import java.lang.ref.WeakReference


@SuppressLint("Registered")
abstract class BaseActivity<T: ViewModel, V: ViewBinding> : AppCompatActivity(), ILce, BaseActivityInit {
    abstract val viewModel: T
    abstract val layoutId: Int
    lateinit var dataBinding: V
    /**
     * Activity中显示加载等待的控件。
     */
    private var loading: View? = null

    /**
     * Activity中由于服务器异常导致加载失败显示的布局。
     */
    private var loadErrorView: View? = null

    /**
     * Activity中由于网络异常导致加载失败显示的布局。
     */
    private var badNetworkView: View? = null

    /**
     * Activity中当界面上没有任何内容时展示的布局。
     */
    private var noContentView: View? = null

    private var defaultLce: ILce? = null

    private var weakRefActivity: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        //沉浸式状态栏配置，使用了工具类判断当前系统版本
        if (!AndroidVersion.hasV()) {
            transparentStatusBar()
        } else {
            enableEdgeToEdge()
        }
        setContentView(getLayoutView())
        //郭霖书中提到过的Activity集中管理工具类
        ActivityCollector.add(WeakReference(this))
        weakRefActivity = WeakReference(this)
        //子类实现，记住两个方法的执行时期为onCreate()，逻辑抽离到两个独立的方法中
        initView()
        //初始化的时候需要进行一次数据初始化
        initData()
    }
    //数据刷新，后续将在接收广播（收藏事件）后触发，这里重写一遍让子类可选
    override fun initData() {}
    //重写该方法，扩充，使用getLayoutView()满足setContentView同时添加LCE初始化的方法
    override fun setContentView(view: View?) {
        super.setContentView(view)
        setupViews()
    }
    //实现LCE的核心方法
    protected open fun setupViews() {
        //读取lce视图文件
        val view = View.inflate(this, R.layout.layout_lce, null)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        //横竖屏的状态栏高度不一样，优化横屏体验
        params.setMargins(
            0,
            dp2px(if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT || isSearchPage()) 70f else 55f),
            0,
            0
        )
        //把lce视图文件添加到activity的最上层
        addContentView(view, params)
        //从lce视图文件中获取元素
        loading = view.findViewById(R.id.loading)
        noContentView = view.findViewById(R.id.noContentView)
        badNetworkView = view.findViewById(R.id.badNetworkView)
        loadErrorView = view.findViewById(R.id.loadErrorView)
        //设置日志供调试
        if (loading == null) {
            Log.e(TAG, "loading is null")
        }
        if (badNetworkView == null) {
            Log.e(TAG, "badNetworkView is null")
        }
        if (loadErrorView == null) {
            Log.e(TAG, "loadErrorView is null")
        }
        //代理类，传入参数（在UI类中初始化视图），由它代理实现LCE接口方法（解耦）
        defaultLce = DefaultLceImpl(
            loading,
            loadErrorView,
            badNetworkView,
            noContentView
        )
        //基类实现了LCE接口，其家族可自由调用接口方法控制lce页面显示
        loadFinished()
    }
    //补丁方法
    protected open fun isSearchPage(): Boolean {
        return false
    }

    /**
     * 设置 LiveData 的状态，根据不同状态显示不同页面
     *
     * @param dataLiveData 将来子类传入的livedata正是BaseViewModel中的成员变量
     * @param onDataStatus 该函数代表数据请求成功后的逻辑（要拿数据怎么办）
     */
    fun <T> setDataStatus(dataLiveData: LiveData<Result<T>>, onDataStatus: (T) -> Unit) {
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
                showToast(getString(R.string.bad_network_view_tip))
                //传入的initData()代表点击网络错误的图后进行数据刷新
                showBadNetworkView { initData() }
            }
        }
    }
    //代理实现
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
    //统一管理，避免泄露
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.remove(weakRefActivity)
    }

    companion object {
        private const val TAG = "BaseActivity"
    }

}

