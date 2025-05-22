package com.gta.core

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import com.gta.core.view.custom.mc.MediaSourceBar

class DataBindingAdapter {
    companion object {
        @BindingAdapter("media_source_icon", "media_source_name")
        @JvmStatic
        fun setMediaSourceInfo(view: MediaSourceBar, drawable: Drawable?, name: String?) {
            name?.let { view.setMediaSourceName(it) }
            drawable?.let { view.setMediaSourceIcon(it) }
        }


    }
}