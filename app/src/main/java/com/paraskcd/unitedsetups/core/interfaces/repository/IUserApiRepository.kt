package com.paraskcd.unitedsetups.core.interfaces.repository

import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.domain.model.User

interface IUserApiRepository {
    suspend fun getUserById(userId: String): DataOrException<User, Exception>
    suspend fun getMyProfile(): DataOrException<User, Exception>
}