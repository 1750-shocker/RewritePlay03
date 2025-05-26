package com.gta.rewriteplay03.mediaActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.gta.core.view.base.BaseViewModel
import com.gta.model.room.entity.ProjectClassify
import com.gta.rewriteplay03.project.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : BaseViewModel() {
    //TODO:3/13/23 这里要写一个数据结构保存tab，提供一个获取tab的方法刷新数据结构，然后提供一个方法给出数据结构
    var position = 0
    val dataList = ArrayList<Unit>()
    private val pageLiveData = MutableLiveData<Boolean>()

    init {
        //一上来就要请求数据
        getDataList(false)
    }

    fun getDataList(key: Boolean) {
        pageLiveData.value = key
    }

    val dataLiveData = pageLiveData.switchMap { key -> getData(key) }

    fun getData(key: Boolean): LiveData<Result<List<ProjectClassify>>> {
        //设置具体的数据请求方法
        return projectRepository.getProjectTree(key)
    }
}