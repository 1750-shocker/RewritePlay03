package com.gta.core.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

abstract class BaseViewModel<BaseData, Data, Key> : ViewModel() {

    //数据保持（列表数据）
    val dataList = ArrayList<Data>()

    private val pageLiveData = MutableLiveData<Key>()

    val dataLiveData = pageLiveData.switchMap { page ->
        getData(page)
    }

    //子类ViewModel实现该方法，一般是调用Repository层
    abstract fun getData(page: Key): LiveData<Result<BaseData>>

    //UI层调用该方法，触发LiveData的监听，从而触发getData(page)方法
    fun getDataList(page: Key) {
        pageLiveData.value = page!!
    }

}
