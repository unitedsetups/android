package com.paraskcd.unitedsetups.core.interfaces.repository

import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.core.requests.postthreads.CreateNewPostThreadRequest
import com.paraskcd.unitedsetups.domain.model.PostThread
import java.lang.Exception

interface IPostThreadApiRepository {
    suspend fun createNewPostThread(request: CreateNewPostThreadRequest): DataOrException<PostThread, Exception>
    suspend fun likePostThread(postThreadId: String): DataOrException<PostThread, Exception>
    suspend fun dislikePostThread(postThreadId: String): DataOrException<PostThread, Exception>
}