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

        Play.initialize(applicationContext)
        LoggerManager.enableLog(true)
            .logLevel(BaseLog.LEVEL_INFO)
            .setGlobalTag("MediaCenter")
            .setMessagePrefix("JDO")
        super.onCreate()
    }

}
