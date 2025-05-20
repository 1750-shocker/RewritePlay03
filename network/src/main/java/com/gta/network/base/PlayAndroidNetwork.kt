package com.gta.network.base

import com.gta.network.service.ProjectService

object PlayAndroidNetwork {

    private val projectService = ServiceCreator.create(ProjectService::class.java)

    suspend fun getProjectTree() = projectService.getProjectTree()

    suspend fun getProject(page: Int, cid: Int) = projectService.getProject(page, cid)
}