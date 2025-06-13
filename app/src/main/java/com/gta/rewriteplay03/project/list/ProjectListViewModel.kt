package com.gta.rewriteplay03.project.list

import androidx.lifecycle.LiveData
import com.gta.core.view.base.BaseAndroidViewModel
import com.gta.model.pojo.QueryArticle
import com.gta.model.room.entity.Article
import com.gta.rewriteplay03.project.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : BaseAndroidViewModel<List<Article>, Article, QueryArticle>() {

    override fun getData(page: QueryArticle): Flow<Result<List<Article>>> {
        return projectRepository.getProject(page)
    }

}