package com.paraskcd.unitedsetups.data.repository.authentication

import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.data.source.authentication.requests.LoginRequest
import com.paraskcd.unitedsetups.data.source.authentication.requests.RegisterRequest
import com.paraskcd.unitedsetups.domain.model.Auth

interface IAuthApiRepository {
    suspend fun Login(request: LoginRequest): DataOrException<Auth, java.lang.Exception>
    suspend fun Register(request: RegisterRequest): DataOrException<Auth, java.lang.Exception>
}