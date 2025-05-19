package com.gta.rewriteplay03.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val pageLiveData = MutableLiveData<Int>()

    fun setPage(page: Int) {
        pageLiveData.value = page
    }

    fun getPage():Int? {
        return pageLiveData.value
    }

}