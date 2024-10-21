package com.paraskcd.unitedsetups.core.interfaces.apis

import com.paraskcd.unitedsetups.core.entities.posts.PostApiEntity
import com.paraskcd.unitedsetups.core.requests.posts.CreateNewPostRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface IPostApi {
    @POST("posts")
    suspend fun createNewPost(@Body request: CreateNewPostRequest): PostApiEntity

    @GET("posts")
    suspend fun getPosts(@QueryMap request: Map<@JvmSuppressWildcards String, @JvmSuppressWildcards Any>): List<PostApiEntity>

    @GET("posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: String): PostApiEntity

    @PUT("posts/{postId}/like")
    suspend fun likePost(@Path("postId") postId: String): PostApiEntity

    @PUT("posts/{postId}/dislike")
    suspend fun dislikePost(@Path("postId") postId: String): PostApiEntity
}