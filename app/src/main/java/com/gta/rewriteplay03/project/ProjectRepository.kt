package com.gta.rewriteplay03.project

import android.app.Application
import com.gta.core.util.DataStoreUtils
import com.gta.model.pojo.QueryArticle
import com.gta.model.room.PlayDatabase
import com.gta.model.room.entity.PROJECT
import com.gta.network.base.PlayAndroidNetwork
import com.gta.rewriteplay03.base.liveDataFire
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.first
import tech.jidouauto.library.logger.Logger
import javax.inject.Inject


@ActivityRetainedScoped
class ProjectRepository @Inject constructor(private val application: Application) {

    private val projectClassifyDao = PlayDatabase.getDatabase(application).projectClassifyDao()
    private val articleListDao = PlayDatabase.getDatabase(application).browseHistoryDao()

    /**
     * 获取项目标题列表
     */
    fun getProjectTree(isRefresh: Boolean) = liveDataFire {
        val projectClassifyLists = projectClassifyDao.getAllProject()
        if (projectClassifyLists.isNotEmpty() && !isRefresh) {
            Result.success(projectClassifyLists)
        } else {
            val projectTree = PlayAndroidNetwork.getProjectTree()
            if (projectTree.errorCode == 0) {
                val projectList = projectTree.data
                projectClassifyDao.insertList(projectList)
                Result.success(projectList)
            } else {
                Result.failure(RuntimeException("response status is ${projectTree.errorCode}  msg is ${projectTree.errorMsg}"))
            }
        }
    }

    /**
     * 获取项目具体文章列表
     * @param query 查询类
     */
    fun getProject(query: QueryArticle) = liveDataFire {
        Logger.info("current query is ${query.cid}&${query.page}&${query.isRefresh}")
        if (query.page == 1) {
            val dataStore = DataStoreUtils
            val articleListForChapterId =
                articleListDao.getArticleListForChapterId(PROJECT, query.cid)
            var downArticleTime = 0L
            dataStore.readLongFlow(DOWN_PROJECT_ARTICLE_TIME, System.currentTimeMillis()).first {
                downArticleTime = it
                true
            }
            if (articleListForChapterId.isNotEmpty() && downArticleTime > 0 && downArticleTime - System.currentTimeMillis() < FOUR_HOUR && !query.isRefresh) {
                Result.success(articleListForChapterId)
            } else {
                val projectTree = PlayAndroidNetwork.getProject(query.page, query.cid)
                if (projectTree.errorCode == 0) {
                    if (articleListForChapterId.isNotEmpty() && articleListForChapterId[0].link == projectTree.data.datas[0].link && !query.isRefresh) {
                        Result.success(articleListForChapterId)
                    } else {
                        projectTree.data.datas.forEach {
                            it.localType = PROJECT
                        }
                        dataStore.saveLongData(
                            DOWN_PROJECT_ARTICLE_TIME,
                            System.currentTimeMillis()
                        )
                        if (query.isRefresh) {
                            articleListDao.deleteAll(PROJECT, query.cid)
                        }
                        articleListDao.insertList(projectTree.data.datas)
                        Result.success(projectTree.data.datas)
                    }
                } else {
                    Result.failure(RuntimeException("response status is ${projectTree.errorCode}  msg is ${projectTree.errorMsg}"))
                }
            }
        } else {
            val projectTree = PlayAndroidNetwork.getProject(query.page, query.cid)
            if (projectTree.errorCode == 0) {
                Result.success(projectTree.data.datas)
            } else {
                Result.failure(RuntimeException("response status is ${projectTree.errorCode}  msg is ${projectTree.errorMsg}"))
            }
        }
    }

}

const val ONE_DAY = 1000 * 60 * 60 * 24
const val FOUR_HOUR = 1000 * 60 * 60 * 4
const val DOWN_IMAGE_TIME = "DownImageTime"
const val DOWN_TOP_ARTICLE_TIME = "DownTopArticleTime"
const val DOWN_ARTICLE_TIME = "DownArticleTime"
const val DOWN_PROJECT_ARTICLE_TIME = "DownProjectArticleTime"
const val DOWN_OFFICIAL_ARTICLE_TIME = "DownOfficialArticleTime"