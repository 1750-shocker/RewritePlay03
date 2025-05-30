package com.gta.core.view.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.bumptech.glide.Glide
import com.gta.core.R
import com.gta.core.databinding.LayoutUserInfoBarBinding

class UserInfoBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: LayoutUserInfoBarBinding =
        LayoutUserInfoBarBinding.inflate(LayoutInflater.from(context))
    private var headIcon: Drawable? = null
    private var userName: String? = null
    private var headSize: Int? = null
    init {
        initAttrs(context, attrs, defStyleRes, defStyleAttr)
        headIcon?.let {
            binding.userHeadIcon.setImageDrawable(it)
        }
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
            R.styleable.UserInfoBar,
            defStyleAttr ?: 0,
            defStyleRes ?: 0
        ) {
            headIcon = getDrawable(R.styleable.UserInfoBar_user_head_icon)
        }
    }

    fun setUserHeadIcon(drawable: Drawable) {
        binding.userHeadIcon.setImageDrawable(drawable)
    }

    fun setUserHeadIcon(resId: Int) {
        binding.userHeadIcon.setImageResource(resId)
    }

    fun setUserHeadIcon(uri: String) {
        Glide.with(context).load(uri).into(binding.userHeadIcon)
    }
}