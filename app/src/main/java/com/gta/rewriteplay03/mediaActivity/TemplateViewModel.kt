package com.gta.rewriteplay03.mediaActivity

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.gta.core.view.base.BaseViewModel
import com.gta.model.pojo.QueryArticle
import com.gta.model.room.entity.Article
import com.gta.rewriteplay03.project.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : BaseViewModel() {
    //TODO:3/14/11 一个私有livedata，一个loadChild方法根据id获取数据刷新livedata，提供一个方法获取livedata
    val dataList = ArrayList<Article>()
    private val pageLiveData = MutableLiveData<QueryArticle>()

    fun getDataList(key: QueryArticle) {
        pageLiveData.value = key
    }

    val dataLiveData = pageLiveData.switchMap { key -> getData(key) }

    fun getData(key: QueryArticle): LiveData<Result<List<Article>>> {
        return projectRepository.getProject(key)
    }
}