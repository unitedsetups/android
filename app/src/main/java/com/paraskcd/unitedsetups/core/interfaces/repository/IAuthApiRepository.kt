package com.paraskcd.unitedsetups.core.interfaces.repository

import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.core.requests.authentication.LoginRequest
import com.paraskcd.unitedsetups.core.requests.authentication.RegisterRequest
import com.paraskcd.unitedsetups.domain.model.Auth
import java.lang.Exception

interface IAuthApiRepository {
    suspend fun Login(request: LoginRequest): DataOrException<Auth, Exception>
    suspend fun Register(request: RegisterRequest): DataOrException<Auth, Exception>
}