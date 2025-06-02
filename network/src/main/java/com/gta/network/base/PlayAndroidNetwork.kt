package com.gta.network.base

import com.gta.network.service.ProjectService
import com.gta.network.service.CollectService

object PlayAndroidNetwork {

    private val projectService = ServiceCreator.create(ProjectService::class.java)

    suspend fun getProjectTree() = projectService.getProjectTree()

    suspend fun getProject(page: Int, cid: Int) = projectService.getProject(page, cid)

    private val collectService = ServiceCreator.create(CollectService::class.java)

    suspend fun getCollectList(page: Int) = collectService.getCollectList(page)

    suspend fun toCollect(id: Int) = collectService.toCollect(id)

    suspend fun cancelCollect(id: Int) = collectService.cancelCollect(id)
}