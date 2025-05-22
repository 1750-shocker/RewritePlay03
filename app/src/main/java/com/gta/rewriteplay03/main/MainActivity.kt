package com.gta.rewriteplay03.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import com.gta.core.util.showToast
import com.gta.core.view.base.BaseActivity
import com.gta.rewriteplay03.R
import com.gta.rewriteplay03.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val viewModel: MainViewModel by viewModels()
    override val layoutId = R.layout.activity_main

//    private lateinit var binding: ActivityMainBinding
//    private val viewModel by viewModels<MainViewModel>()
    private var isPort = true

    override fun initView() {
        isPort = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
//        when (isPort) {
//            true -> dataBinding.homeView?.init(supportFragmentManager, viewModel)
//            false -> dataBinding.homeLandView?.init(supportFragmentManager, viewModel)
//        }
        dataBinding.homeView.init(supportFragmentManager, viewModel)
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState)  // 解决fragment重影
    }

    override fun getLayoutView(): View {
//        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        return dataBinding.root
    }

    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            showToast(getString(com.gta.core.R.string.exit_program))
            exitTime = System.currentTimeMillis()
        } else {
            exitProcess(0)
        }
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

}