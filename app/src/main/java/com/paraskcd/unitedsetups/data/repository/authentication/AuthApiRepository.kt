package com.paraskcd.unitedsetups.data.repository.authentication

import android.util.Log
import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.data.source.authentication.AuthApi
import com.paraskcd.unitedsetups.data.source.authentication.entity.toAuth
import com.paraskcd.unitedsetups.data.source.authentication.requests.LoginRequest
import com.paraskcd.unitedsetups.data.source.authentication.requests.RegisterRequest
import com.paraskcd.unitedsetups.domain.model.Auth
import javax.inject.Inject

class AuthApiRepository @Inject constructor(private val authApi: AuthApi): IAuthApiRepository {
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