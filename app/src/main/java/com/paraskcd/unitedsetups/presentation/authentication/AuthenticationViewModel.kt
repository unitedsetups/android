package com.paraskcd.unitedsetups.presentation.authentication

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paraskcd.unitedsetups.core.common.TokenManager
import com.paraskcd.unitedsetups.core.interfaces.repository.IAuthApiRepository
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

    var isLoading by mutableStateOf(false)
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

    init {
        isLoggedIn = tokenManager.isLoggedIn()
    }

    suspend fun login(context: Context) {
        isLoading = true
        try {
            val response = authApiRepository.Login(LoginRequest(email, password))
            response.ex?.let { e ->
                throw Exception(e)
            }
            response.data?.let { data ->
                tokenManager.saveToken(authData = response.data)
                isLoggedIn = true
                isLoading = false
            }
        } catch (e: Exception) {
            if (e.message != null) {
                if (e.message!!.contains("401")) {
                    Toast.makeText(context, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
            } else {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        }
    }

    suspend fun register(context: Context) {
        isLoading = true
        try {
            val response = authApiRepository.Register(RegisterRequest(username, email, name, password))
            response.ex?.let { e ->
                throw Exception(e)
            }
            response.data?.let { data ->
                tokenManager.saveToken(authData = response.data)
                isLoggedIn = true
            }
        } catch (e: Exception) {
            if (e.message != null) {
                if (e.message!!.contains("400")) {
                    Toast.makeText(context, "Please check your inputs", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
            } else {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        }
    }

    fun signOut() {
        tokenManager.logout()
        isLoggedIn = false
    }
}