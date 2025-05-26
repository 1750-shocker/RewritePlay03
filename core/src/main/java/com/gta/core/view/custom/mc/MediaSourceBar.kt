package com.gta.core.view.custom.mc

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.bumptech.glide.Glide
import com.gta.core.R
import com.gta.core.databinding.LayoutMediaSourceBarBinding

class MediaSourceBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.mediaSourceBarStyle,
    defStyleRes: Int = R.style.MediaSourceBarStyle
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: LayoutMediaSourceBarBinding =
        LayoutMediaSourceBarBinding.inflate(LayoutInflater.from(context))
    private var mTitleTextColor: Int? = null //颜色
    private var mTitleText: String? = null //标题
    private var mBackgroundColor: Int? = null //背景颜色
    private var mIcon: Drawable? = null//图标
    private var mTitleTextSize: Float? = null

    init {
        initAttrs(context, attrs, defStyleAttr, R.attr.mediaSourceBarStyle)
        mBackgroundColor?.let { binding.mediaSourceBar.setBackgroundColor(it) }
        mTitleTextSize?.let {  binding.mediaSourceTitle.textSize = it }
        mTitleTextColor?.let { binding.mediaSourceTitle.setTextColor(it) }
        mIcon?.let { binding.mediaSourceIcon.setImageDrawable(it) }
        mTitleText?.let { binding.mediaSourceTitle.text = it }
        binding.root.isClickable = true
        addView(binding.root)
    }

    private fun initAttrs(
        context: Context,
        attrs: AttributeSet?,
        defStyleRes: Int?,
        defStyleAttr: Int?
    ) {
        context.withStyledAttributes(
            attrs,
            R.styleable.MediaSourceBar,
            defStyleAttr ?: R.attr.mediaSourceBarStyle,
            defStyleRes ?: R.style.MediaSourceBarStyle
        ) {
            mBackgroundColor = getColor(
                R.styleable.MediaSourceBar_media_source_backgroundColor,
                Color.TRANSPARENT
            )
            mTitleText = getString(R.styleable.MediaSourceBar_media_source_name)
            mTitleTextColor =
                getColor(R.styleable.MediaSourceBar_media_source_name_color, Color.BLACK)
            mTitleTextSize = getDimension(R.styleable.MediaSourceBar_media_source_name_size, 36f)
            mIcon = getDrawable(R.styleable.MediaSourceBar_media_source_icon)
        }
    }


    override fun setOnClickListener(listener: OnClickListener?) {
        Log.d("wzhh", "setOnClickListener: 执行到mediaSourceBar内部方法")
        binding.root.setOnClickListener(listener)
    }

    fun setMediaSourceIcon(uri: Uri) {
        Glide.with(context).load(uri).into(binding.mediaSourceIcon)
    }

    fun setMediaSourceIcon(drawable: Drawable) {
        binding.mediaSourceIcon.setImageDrawable(drawable)
    }

    fun setMediaSourceIcon(resId: Int) {
        binding.mediaSourceIcon.setImageResource(resId)
    }

    fun setMediaSourceName(name: String) {
        binding.mediaSourceTitle.text = name
    }

    fun setMediaSourceTitleSize(size: Float) {
        binding.mediaSourceTitle.textSize = size
    }

    fun setMediaSourceTitleColor(color: Int) {
        binding.mediaSourceTitle.setTextColor(color)
    }

    fun setMediaSourceBackgroundColor(color: Int) {
        binding.mediaSourceBar.setBackgroundColor(color)
    }

}