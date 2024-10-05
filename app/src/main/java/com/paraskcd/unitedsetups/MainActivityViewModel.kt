package com.paraskcd.unitedsetups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paraskcd.unitedsetups.core.common.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val tokenManager: TokenManager) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return tokenManager.isLoggedIn()
    }
}