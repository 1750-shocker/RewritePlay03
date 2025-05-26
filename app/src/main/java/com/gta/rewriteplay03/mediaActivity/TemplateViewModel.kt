package com.gta.rewriteplay03.mediaActivity

import android.content.Context
import androidx.core.content.ContextCompat
import com.gta.core.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
) : BaseViewModel() {
//TODO:3/14/11 一个私有livedata，一个loadChild方法根据id获取数据刷新livedata，提供一个方法获取livedata
}