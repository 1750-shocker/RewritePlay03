package com.gta.rewriteplay03.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.gta.core.util.showToast
import com.gta.core.view.base.BaseActivity
import com.gta.core.view.custom.LoginDialogFragment
import com.gta.rewriteplay03.R
import com.gta.rewriteplay03.databinding.ActivityMainBinding
import com.gta.rewriteplay03.mediaActivity.MediaActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val viewModel: MainViewModel by viewModels()
    override val layoutId = R.layout.activity_main

    private var exitTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.button.setOnClickListener {
            Toast.makeText(this, "123", Toast.LENGTH_SHORT).show()
            MediaActivity.actionStart(this)
        }
        dataBinding.userHead.setOnClickListener {
            LoginDialogFragment().showDialog(this)
        }
    }

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