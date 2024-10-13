package com.paraskcd.unitedsetups.core.interfaces.apis

import com.paraskcd.unitedsetups.core.entities.posts.PostApiEntity
import com.paraskcd.unitedsetups.core.requests.posts.CreateNewPostRequest
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface IPostApi {
    @POST("posts")
    suspend fun createNewPost(request: CreateNewPostRequest): PostApiEntity

    @GET("posts")
    suspend fun getPosts(@QueryMap request: Map<@JvmSuppressWildcards String, @JvmSuppressWildcards Any>): List<PostApiEntity>
}