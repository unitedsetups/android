package com.paraskcd.unitedsetups.data.repository.posts

import android.util.Log
import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.core.interfaces.apis.IPostApi
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.core.entities.posts.toPost
import com.paraskcd.unitedsetups.core.requests.posts.CreateNewPostRequest
import com.paraskcd.unitedsetups.core.requests.posts.GetPostsRequest
import com.paraskcd.unitedsetups.core.requests.posts.toQueryMap
import com.paraskcd.unitedsetups.domain.model.Post
import java.lang.Exception
import javax.inject.Inject

class PostApiRepository @Inject constructor(private val postApi: IPostApi) : IPostApiRepository {
    override suspend fun createNewPost(request: CreateNewPostRequest): DataOrException<Post, Exception> {
        return try {
            DataOrException(postApi.createNewPost(request).toPost(), null)
        } catch (ex: Exception) {
            Log.e("createNewPost", ex.message.toString())
            DataOrException(null, ex)
        }
    }

    override suspend fun getPosts(request: GetPostsRequest): DataOrException<List<Post>, Exception> {
        return try {
            DataOrException(postApi.getPosts(request.toQueryMap()).map { it.toPost() }, null)
        } catch (ex: Exception) {
            Log.e("getPosts", ex.message.toString())
            DataOrException(null, ex)
        }
    }
}