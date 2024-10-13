package com.paraskcd.unitedsetups.data.repository.authentication

import android.util.Log
import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.core.interfaces.apis.IAuthApi
import com.paraskcd.unitedsetups.core.interfaces.repository.IAuthApiRepository
import com.paraskcd.unitedsetups.core.entities.authentication.toAuth
import com.paraskcd.unitedsetups.core.requests.authentication.LoginRequest
import com.paraskcd.unitedsetups.core.requests.authentication.RegisterRequest
import com.paraskcd.unitedsetups.domain.model.Auth
import javax.inject.Inject

class AuthApiRepository @Inject constructor(private val authApi: IAuthApi): IAuthApiRepository {
    override suspend fun Login(request: LoginRequest): DataOrException<Auth, java.lang.Exception> {
        return try {
            DataOrException(authApi.Login(request).toAuth(), null)
        } catch (ex: Exception) {
            Log.e("Login", ex.message.toString())
            DataOrException(null, ex)
        }
    }

    override suspend fun Register(request: RegisterRequest): DataOrException<Auth, java.lang.Exception> {
        return try {
            DataOrException(authApi.Register(request).toAuth(), null)
        } catch (ex: Exception) {
            Log.e("Register", ex.message.toString())
            DataOrException(null, ex)
        }
    }
}