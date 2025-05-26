package com.gta.rewriteplay03.mediaActivity

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.gta.core.view.base.BaseFragment
import com.gta.rewriteplay03.R
import com.gta.rewriteplay03.databinding.TemplateFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplateFragment private constructor(): BaseFragment<TemplateViewModel, TemplateFragmentBinding>() {
    companion object {
        const val MEDIA_ITEM_MEDIA_ID = "MEDIA_ITEM_MEDIA_ID"
        const val MEDIA_ITEM_TITLE = "MEDIA_ITEM_TITLE"

        //TODO: 3/13/23 通过给定的mediaItem，传入bundle来创建fragment
        /*fun getTemplateFragment(mediaItem: MediaItem): TemplateFragment {
            return TemplateFragment().apply {
                val extras = Bundle()
                mediaItem.mediaMetadata.title.let {
                    extras.putString(MEDIA_ITEM_MEDIA_ID, it.toString())
                }
            }
        }*/
    }
    override val viewModel: TemplateViewModel  by viewModels()

    override val layoutId: Int = R.layout.template_fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO:3/14/11 这里读取bundle，然后根据bundle的内容来调用viewmodel的方法获取展示数据
    }
}