package com.gta.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.Size
import androidx.annotation.ColorInt
import com.gta.core.R
import kotlin.math.ceil


object BitmapUtils {
    private const val TAG = "BitmapUtils"

    /**
     * Scales a bitmap while preserving the proportions such that both dimensions are the smallest
     * values possible that are equal to or larger than the given dimensions.
     *
     * This function can be a few times as expensive as Bitmap.createScaledBitmap with
     * filtering when downscaling, but it produces much nicer results.
     *
     * @param bm The bitmap to scale.
     * @param width The desired width.
     * @param height The desired height.
     * @return The scaled bitmap, or the original bitmap if scaling was not necessary.
     */
    fun scaleBitmap(bm: Bitmap?, width: Int, height: Int): Bitmap? {
        if (bm == null || (bm.height == height && bm.width == width)) {
            return bm
        }

        var heightScale = 1f
        if (bm.height > height) {
            heightScale = height.toFloat() / bm.height
        }
        var widthScale = 1f
        if (bm.width > width) {
            widthScale = width.toFloat() / bm.width
        }
        val scale = if (heightScale > widthScale) heightScale else widthScale
        val scaleWidth = ceil((bm.width * scale).toDouble()) as Int
        val scaleHeight = ceil((bm.height * scale).toDouble()) as Int

        var scaledBm: Bitmap = bm
        // If you try to scale an image down too much in one go, you can end up with discontinuous
        // interpolation. Therefore, if necessary, we scale the image to twice the desired size
        // and do a second scaling to the desired size, which smooths jaggedness from the first go.
        if (scale < .5f) {
            scaledBm = Bitmap.createScaledBitmap(scaledBm, scaleWidth * 2, scaleHeight * 2, true)
        }

        if (scale != 1f) {
            val newScaledBitmap = Bitmap
                .createScaledBitmap(scaledBm, scaleWidth, scaleHeight, true)
            if (scaledBm != bm) {
                scaledBm.recycle()
            }
            scaledBm = newScaledBitmap
        }
        return scaledBm
    }

    /**
     * Crops the given bitmap to a centered rectangle of the given dimensions.
     *
     * @param bm the bitmap to crop.
     * @param width the width to crop to.
     * @param height the height to crop to.
     * @return The cropped bitmap, or the original if no cropping was necessary.
     */
    fun cropBitmap(bm: Bitmap?, width: Int, height: Int): Bitmap? {
        if (bm == null) {
            return bm
        }
        if (bm.height < height || bm.width < width) {
            if (Log.isLoggable(TAG, Log.INFO)) {
                Log.i(
                    TAG, String.format(
                        "Can't crop bitmap to larger dimensions (%d, %d) -> (%d, %d).",
                        bm.width, bm.height, width, height
                    )
                )
            }
            return bm
        }
        val x = (bm.width - width) / 2
        val y = (bm.height - height) / 2
        return Bitmap.createBitmap(bm, x, y, width, height)
    }

    /** Creates a copy of the given bitmap and applies the given color over the result  */
    fun createTintedBitmap(image: Bitmap?, @ColorInt colorOverlay: Int): Bitmap? {
        if (image == null) return null
        val clone = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(clone)
        canvas.drawBitmap(image, 0f, 0f, Paint())
        canvas.drawColor(colorOverlay)
        return clone
    }

    /** Returns a tinted drawable if flagging is enabled and the given drawable is a bitmap.  */
    fun maybeFlagDrawable(context: Context, drawable: Drawable): Drawable {
        var drawable = drawable
        if (drawable is BitmapDrawable) {
            val flags: CommonFlags = CommonFlags.getInstance(context)!!
            val bitmap = drawable.bitmap
            if (flags.shouldFlagImproperImageRefs() && bitmap != null) {
                val res = context.resources
                val tint =
                    context.getColor(R.color.improper_image_refs_tint_color)
                drawable = BitmapDrawable(res, createTintedBitmap(bitmap, tint))
            }
        }
        return drawable
    }

    /** Renders the drawable into a bitmap if needed.  */
    fun fromDrawable(drawable: Drawable, bitmapSize: Size?): Bitmap {
        var bitmapSize = bitmapSize
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }

        val matrix = Matrix()
        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmapSize = Size(1, 1)
            drawable.setBounds(0, 0, bitmapSize.width, bitmapSize.height)
        } else {
            if (bitmapSize == null) {
                bitmapSize = Size(drawable.intrinsicWidth, drawable.intrinsicHeight)
            }
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val srcR = RectF(
                0f, 0f, drawable.intrinsicWidth.toFloat(),
                drawable.intrinsicHeight.toFloat()
            )
            val dstR = RectF(0f, 0f, bitmapSize.width.toFloat(), bitmapSize.height.toFloat())
            matrix.setRectToRect(srcR, dstR, Matrix.ScaleToFit.CENTER)
        }

        val bitmap = Bitmap.createBitmap(
            bitmapSize.width, bitmapSize.height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        canvas.setMatrix(matrix)
        drawable.draw(canvas)

        return bitmap
    }
}