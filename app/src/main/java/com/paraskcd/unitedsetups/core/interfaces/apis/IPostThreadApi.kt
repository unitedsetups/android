package com.paraskcd.unitedsetups.core.interfaces.apis

import com.paraskcd.unitedsetups.core.entities.postthreads.PostThreadApiEntity
import com.paraskcd.unitedsetups.core.requests.postthreads.CreateNewPostThreadRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IPostThreadApi {
    @POST("postthreads")
    suspend fun createNewPostThread(@Body request: CreateNewPostThreadRequest): PostThreadApiEntity

    @PUT("postthreads/{postThreadId}/like")
    suspend fun likePostThread(@Path("postThreadId") postThreadId: String): PostThreadApiEntity

    @PUT("postthreads/{postThreadId}/dislike")
    suspend fun dislikePostThread(@Path("postThreadId") postThreadId: String): PostThreadApiEntity
}