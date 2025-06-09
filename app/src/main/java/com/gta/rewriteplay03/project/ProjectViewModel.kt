package com.gta.rewriteplay03.project

import androidx.lifecycle.LiveData
import com.gta.core.view.base.BaseAndroidViewModel
import com.gta.model.room.entity.ProjectClassify
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) :
    BaseAndroidViewModel<List<ProjectClassify>, Unit, Boolean>() {

    var position = 0

    override fun getData(page: Boolean): LiveData<Result<List<ProjectClassify>>> {
        return projectRepository.getProjectTree(page)
    }

    init {
        getDataList(false)
    }

}