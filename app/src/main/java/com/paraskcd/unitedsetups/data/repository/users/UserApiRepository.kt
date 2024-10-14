package com.paraskcd.unitedsetups.data.repository.users

import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.core.entities.users.toUser
import com.paraskcd.unitedsetups.core.interfaces.apis.IUserApi
import com.paraskcd.unitedsetups.core.interfaces.repository.IUserApiRepository
import com.paraskcd.unitedsetups.domain.model.User
import javax.inject.Inject

class UserApiRepository @Inject constructor(private val userApi: IUserApi) : IUserApiRepository {
    override suspend fun getUserById(userId: String): DataOrException<User, Exception> {
        return try {
            DataOrException(userApi.getUserById(userId).toUser(), null)
        } catch (ex: Exception) {
            DataOrException(null, ex)
        }
    }

    override suspend fun getMyProfile(): DataOrException<User, Exception> {
        return try {
            DataOrException(userApi.getMyProfile().toUser(), null)
        } catch (ex: Exception) {
            DataOrException(null, ex)
        }
    }
}