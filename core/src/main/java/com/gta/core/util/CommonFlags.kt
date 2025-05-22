package com.gta.core.util

import android.content.Context
import com.gta.core.R

class CommonFlags private constructor(context: Context) {
    private val mApplicationContext: Context = context.applicationContext
    private var mFlagImproperImageRefs: Boolean? = null

    /**
     * Returns whether improper image references should be flagged (typically tinting the images
     * in red with [R.color.improper_image_refs_tint_color]. This special mode is intended for
     * third party app developers so they can notice quickly that they are sending improper image
     * references. Such references include :
     *  * remote image instead of a local content uri
     *  * bitmap sent over the binder instead of a local content uri
     *  * bitmap icon instead of a vector drawable
     *
     *
     *
     * To activate, either overlay R.bool.flag_improper_image_references to true, or use adb:
     * `
     * adb root
     * adb shell setprop com.android.car.apps.common.FlagNonLocalImages 1
     * adb shell am force-stop APP_PACKAGE # eg: APP_PACKAGE= com.android.car.media
    ` *
     */
    fun shouldFlagImproperImageRefs(): Boolean {
        if (mFlagImproperImageRefs == null) {
            val res = mApplicationContext.resources
            mFlagImproperImageRefs = res.getBoolean(R.bool.flag_improper_image_references)/*|| "1".equals(SystemProperties.get(FLAG_IMPROPER_IMAGE_REFS_KEY, "0"))*/
        }
        return mFlagImproperImageRefs as Boolean
    }

    companion object {
        private const val FLAG_IMPROPER_IMAGE_REFS_KEY =
            "com.android.car.apps.common.FlagNonLocalImages"

        // We store the application context, not an activity.
        private var sInstance: CommonFlags? = null

        /** Returns the singleton.  */
        fun getInstance(context: Context): CommonFlags? {
            if (sInstance == null) {
                sInstance = CommonFlags(context)
            }
            return sInstance
        }
    }
}