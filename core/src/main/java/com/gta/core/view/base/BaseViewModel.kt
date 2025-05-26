package com.gta.core.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

abstract class BaseViewModel : ViewModel() {
    companion object {
        const val DEFAULT_PAGE_SIZE = 0
        const val DEFAULT_START_PAGE = 0
    }

}
