package com.gta.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.Drawable
import androidx.core.graphics.PathParser
import com.gta.core.R


/** A helper to crop icons to a shape using a given or the default path.  */
class IconCropper
/** Sets up the icon cropper with the given icon mask.  */(private val mIconMask: Path) {
    /**
     * Sets up the icon cropper instance with the default crop mask.
     *
     * The SVG path mask is read from the `R.string.config_crop_icon_mask` resource value.
     */
    constructor(context: Context) : this(getDefaultMask(context))

    /** Crops the given drawable according to the current object settings.  */
    fun crop(source: Drawable): Bitmap {
        return crop(BitmapUtils.fromDrawable(source, null))
    }

    /** Crops the given bitmap according to the current object settings.  */
    fun crop(icon: Bitmap): Bitmap {
        val width = icon.width
        val height = icon.height

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint()
        paint.isAntiAlias = true
        // Note: only alpha component of the color set below matters, since we
        // overlay the mask using PorterDuff.Mode.SRC_IN mode (more details here:
        // https://d.android.com/reference/android/graphics/PorterDuff.Mode).
        paint.color = Color.WHITE

        canvas.save()
        canvas.scale(width / ICON_MASK_SIZE, height / ICON_MASK_SIZE)
        canvas.drawPath(mIconMask, paint)
        canvas.restore()

        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(icon, 0f, 0f, paint)

        return output
    }

    companion object {
        private const val ICON_MASK_SIZE = 100.0f

        private fun getDefaultMask(context: Context): Path {
            return PathParser.createPathFromPathData(
                context.getString(R.string.config_crop_icon_mask)
            )
        }
    }
}