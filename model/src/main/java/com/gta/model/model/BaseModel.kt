package com.gta.model.model

data class BaseModel<T>(
    val `data`: T,
    val errorCode: Int,
    val errorMsg: String
)