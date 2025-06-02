package com.gta.rewriteplay03.article.collect

import com.gta.rewriteplay03.base.liveDataModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import com.gta.network.base.PlayAndroidNetwork

@Singleton
class CollectRepository @Inject constructor() {

    /**
     * 获取收藏列表
     *
     * @param page 页码
     */
    fun getCollectList(page: Int) = liveDataModel { PlayAndroidNetwork.getCollectList(page) }

    suspend fun cancelCollects(id: Int) = PlayAndroidNetwork.cancelCollect(id)
    suspend fun toCollects(id: Int) = PlayAndroidNetwork.toCollect(id)

}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CollectRepositoryPoint {
    fun collectRepository(): CollectRepository
}