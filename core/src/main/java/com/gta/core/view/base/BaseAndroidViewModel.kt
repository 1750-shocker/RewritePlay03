package com.gta.core.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn


abstract class BaseAndroidViewModel<BaseData, Data, Key> : ViewModel() {

    //数据保持（列表数据）
    val dataList = ArrayList<Data>()

    protected val _trigger = MutableStateFlow<Key?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val dataFlow: StateFlow<Result<BaseData>> = _trigger
        .filterNotNull()
        .flatMapLatest { key ->
            getData(key)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Result.failure<BaseData>(Throwable("Empty"))
        )

    //UI层调用该方法，触发LiveData的监听，从而触发getData(page)方法
    fun requestData(key: Key) {
        _trigger.value = key
    }

    //子类ViewModel实现该方法，一般是调用Repository层
    protected abstract fun getData(key: Key): Flow<Result<BaseData>>
}
