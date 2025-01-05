package com.paraskcd.unitedsetups.core.interfaces.repository

import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.core.requests.posts.CreateNewPostRequest
import com.paraskcd.unitedsetups.core.requests.posts.GetPostsRequest
import com.paraskcd.unitedsetups.domain.model.Post
import java.lang.Exception

interface IPostApiRepository {
    suspend fun createNewPost(request: CreateNewPostRequest): DataOrException<Post, Exception>
    suspend fun getPosts(request: GetPostsRequest): DataOrException<List<Post>, Exception>
    suspend fun getPostById(postId: String): DataOrException<Post, Exception>
    suspend fun likePost(postId: String): DataOrException<Post, Exception>
    suspend fun dislikePost(postId: String): DataOrException<Post, Exception>
    suspend fun deletePost(postId: String): DataOrException<Unit, Exception>
}