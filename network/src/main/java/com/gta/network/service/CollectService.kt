package com.gta.network.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import com.gta.model.model.Collect
import com.gta.model.model.BaseModel

interface CollectService {

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): BaseModel<Collect>

    @POST("lg/collect/{id}/json")
    suspend fun toCollect(@Path("id") id: Int): BaseModel<Any>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id") id: Int): BaseModel<Any>

}