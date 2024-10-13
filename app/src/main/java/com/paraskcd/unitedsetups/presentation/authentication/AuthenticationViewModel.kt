package com.paraskcd.unitedsetups.presentation.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paraskcd.unitedsetups.core.common.TokenManager
import com.paraskcd.unitedsetups.core.interfaces.repository.IAuthApiRepository
import com.paraskcd.unitedsetups.data.repository.authentication.AuthApiRepository
import com.paraskcd.unitedsetups.core.requests.authentication.LoginRequest
import com.paraskcd.unitedsetups.core.requests.authentication.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authApiRepository: IAuthApiRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var username by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var isLoggedIn by mutableStateOf(false)
        private set

    fun updateEmail(input: String) {
        email = input
    }

    fun updatePassword(input: String) {
        password = input
    }

    fun updateName(input: String) {
        name = input
    }

    fun updateUsername(input: String) {
        username = input
    }

    suspend fun login() {
        val response = authApiRepository.Login(LoginRequest(email, password))
        tokenManager.saveToken(authData = response.data)
        isLoggedIn = true
    }

    suspend fun register(): Exception? {
        val response = authApiRepository.Register(RegisterRequest(username, email, name, password))
        return response.ex
    }
}