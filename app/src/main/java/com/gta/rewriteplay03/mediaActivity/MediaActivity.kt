package com.gta.rewriteplay03.mediaActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gta.core.view.base.BaseActivity
import com.gta.core.view.custom.MediaSourceDialog
import com.gta.model.model.ServiceBean
import com.gta.rewriteplay03.R
import com.gta.rewriteplay03.databinding.ActivityMediaBinding
import com.gta.rewriteplay03.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import tech.jidouauto.library.logger.Logger

@AndroidEntryPoint
class MediaActivity :BaseActivity<MediaViewModel, ActivityMediaBinding>(){

    override val viewModel: MediaViewModel by viewModels()
    override val layoutId: Int = R.layout.activity_media
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TODO:(3/14/23 这里要调用viewModel的方法获取tab数据)
        dataBinding.serviceInfo = ServiceBean("网易云音乐", ContextCompat.getDrawable(this@MediaActivity, R.drawable.ic_project))
        dataBinding.mediaSourceBar.setOnClickListener{
            MediaSourceDialog(this).show()
        }
    }
    companion object {
        private const val TAG = "MediaActivity"
        fun actionStart(context: Context) {
            val intent = Intent(context, MediaActivity::class.java)
            context.startActivity(intent)
        }
    }

}