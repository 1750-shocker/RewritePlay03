package com.gta.core.view.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface BaseInit {
    //初始化数据，包括网络请求、数据库操作等
    fun initData()
    //初始化视图，包括设置布局、绑定数据等
    fun initView()
}

interface BaseActivityInit: BaseInit{
    //获取布局视图
    fun getLayoutView(): View
}

interface BaseFragmentInit: BaseInit{
    //获取布局视图
    fun getLayoutView(inflater: LayoutInflater, container: ViewGroup?, attachToRoot:Boolean):View
}
