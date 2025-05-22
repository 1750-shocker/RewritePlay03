package com.gta.rewriteplay03.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gta.core.view.base.BaseViewModel
import com.gta.model.room.entity.ProjectClassify
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : BaseViewModel<List<ProjectClassify>, Unit, Boolean>() {

    var position = 0

    override fun getData(page: Boolean): LiveData<Result<List<ProjectClassify>>> {
        return projectRepository.getProjectTree(page)
    }

    init {
        getDataList(false)
    }

}