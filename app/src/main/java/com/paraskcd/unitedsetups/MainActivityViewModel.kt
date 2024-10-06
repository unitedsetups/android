package com.paraskcd.unitedsetups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paraskcd.unitedsetups.core.common.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(tokenManager: TokenManager) : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
        private set

    init {
        isLoggedIn = tokenManager.isLoggedIn()
    }
}