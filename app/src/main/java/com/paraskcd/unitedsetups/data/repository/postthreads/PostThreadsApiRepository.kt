package com.paraskcd.unitedsetups.data.repository.postthreads

import android.util.Log
import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.core.entities.posts.toPost
import com.paraskcd.unitedsetups.core.entities.postthreads.toPostThread
import com.paraskcd.unitedsetups.core.interfaces.apis.IPostThreadApi
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostThreadApiRepository
import com.paraskcd.unitedsetups.core.requests.postthreads.CreateNewPostThreadRequest
import com.paraskcd.unitedsetups.domain.model.PostThread
import java.lang.Exception
import javax.inject.Inject

class PostThreadsApiRepository @Inject constructor(private val postThreadsApi: IPostThreadApi) : IPostThreadApiRepository {
    override suspend fun createNewPostThread(request: CreateNewPostThreadRequest): DataOrException<PostThread, java.lang.Exception> {
        return try {
            DataOrException(postThreadsApi.createNewPostThread(request).toPostThread(), null)
        } catch (ex: Exception) {
            Log.e("createNewPostThread", ex.message.toString())
            DataOrException(null, ex)
        }
    }

    override suspend fun likePostThread(postThreadId: String): DataOrException<PostThread, Exception> {
        return try {
            DataOrException(postThreadsApi.likePostThread(postThreadId).toPostThread(), null)
        } catch (ex: Exception) {
            Log.e("likePost", ex.message.toString())
            DataOrException(null, ex)
        }
    }

    override suspend fun dislikePostThread(postThreadId: String): DataOrException<PostThread, Exception> {
        return try {
            DataOrException(postThreadsApi.dislikePostThread(postThreadId).toPostThread(), null)
        } catch (ex: Exception) {
            Log.e("likePost", ex.message.toString())
            DataOrException(null, ex)
        }
    }
}