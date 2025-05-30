package com.gta.core.util


import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import androidx.core.net.toUri

class AccountManager(context: Context, authorities: String): ContentResolver(context) {
    companion object {
        const val QRCODE = "qrCode"
        const val IS_LOGIN = "isLogin"
        const val USER_INFO = "userInfo"
        const val EXTRA_SUCCESS = "call_success"
    }
    private val contentResolver: ContentResolver = context.contentResolver
    private val uri: String = "content://$authorities"

    val handler: Handler = Handler(
        HandlerThread(authorities).apply { start() }.looper
    )

    private val userInfoObserver = object : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            getUserInfo()
        }
    }

    init {
        contentResolver.registerContentObserver("$uri/$USER_INFO".toUri(), false, userInfoObserver)
    }

    /**
     * Get qr code
     * {
     *  "qrCode": "XXXXXXXXXXXXXXXXXX"
     * }
     * @return
     */
    fun getQrCode(): String? {
        val result = contentResolver.call(uri, QRCODE, null, null)
        val isSuccess = result?.getBoolean(EXTRA_SUCCESS)
        if (isSuccess == true) {
            return result.getString(QRCODE)
        }
        return null
    }

    /**
     * Get user info
     *
     * {
     *  "loginStatus": 1
     *  "name": ”xxxx“
     *  "isVip": true
     *  "vipIcon": "XXXXXX"
     * }
     *  loginStatus: -1 means LOGIN_FAILED, 0 means LOGOUT, 1 means LOGIN, 2 means LOGGING
     * @return
     */
    fun getUserInfo(): String? {
        val result = contentResolver.call(uri, USER_INFO, null, null)
        val isSuccess = result?.getBoolean(EXTRA_SUCCESS)
        if (isSuccess == true) {
            return result.getString(USER_INFO)
        }
        return null
    }


    fun isLogin(): Boolean {
        val result = contentResolver.call(uri, IS_LOGIN, null, null)
        val isSuccess = result?.getBoolean(EXTRA_SUCCESS)
        if (isSuccess == true) {
            return result.getBoolean(IS_LOGIN)
        }
        return false
    }

    class Builder {
        private var authorities: String? = null

        fun setAuthorities(authorities: String): Builder {
            return this
        }

        fun build(context: Context): AccountManager {
            if (authorities.isNullOrEmpty()) {
                throw IllegalArgumentException("authorities cannot be null or empty")
            }
            return AccountManager(context, authorities!!)
        }
    }
}