package com.gta.model.model


/*
*  loginStatus: -1 means LOGIN_FAILED, 0 means LOGOUT, 1 means LOGIN, 2 means LOGGING
*/
data class UserInfo(
    val loginStatus: Int? = null,
    val name: String? = null,
    val avatar: String? = null,
    val isVip: Boolean? = null,
    val vipIcon: String? = null,
)
