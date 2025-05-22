package com.gta.rewriteplay03

import android.app.Application
import com.gta.core.Play
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import jp.wasabeef.glide.transformations.BuildConfig
import tech.jidouauto.library.logger.BaseLog
import tech.jidouauto.library.logger.Logger
import tech.jidouauto.library.logger.LoggerManager

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Play.initialize(applicationContext)
        LoggerManager.enableLog(true)
            .logLevel(BaseLog.LEVEL_INFO)
            .setGlobalTag("MediaCenter")
            .setMessagePrefix("JDO")
//        initData()
    }

   /* private fun initData() {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            initBugLy()
        }
    }*/

   /* private fun initBugLy() {
        // Bugly bug上报
        CrashReport.initCrashReport(applicationContext, "0f4f8e06b4", false)
    }*/

    companion object {
        //static 代码段可以防止内存泄露
        init { //设置全局的Header构建器
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(
                    com.gta.core.R.color.refresh,
                    com.gta.core.R.color.text_color
                )//全局设置主题颜色  CustomRefreshHeader   ClassicsHeader
                ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }
}
