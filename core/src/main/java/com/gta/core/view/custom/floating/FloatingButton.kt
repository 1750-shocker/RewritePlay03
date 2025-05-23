package com.gta.core.view.custom.floating

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 *
 * 装载旋转进度按钮位图的按钮，继承自[FloatingActionButton]
 *
 */
class FloatingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatingActionButton(
    context, attrs, defStyleAttr
) {
    private var backgroundHint: ColorStateList? = null

    /**
     * 对fmb进行配置
     *
     * @param backgroundHint fmb背景颜色
     */
    fun config(backgroundHint: ColorStateList?) {
        this.backgroundHint = backgroundHint
        config()
    }

    private fun config() {
        if (backgroundHint != null) {
            backgroundTintList = backgroundHint
        }
    }

    /**
     * 设置按钮背景
     *
     * @param drawable
     */
    fun setCoverDrawable(drawable: Drawable?) {
        if (drawable == null) {
            throw NullPointerException("drawable is not null")
        }
        config()
        setImageDrawable(drawable)
        postInvalidate()
    }


    fun setCover(resId: Int) {
        config()
        setImageResource(resId)
        postInvalidate()
    }

}