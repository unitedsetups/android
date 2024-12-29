package com.paraskcd.unitedsetups.core.common

import android.app.Application.getProcessName
import android.content.Context
import android.content.SharedPreferences
import com.paraskcd.unitedsetups.domain.model.Auth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(getProcessName() + "_preferences", Context.MODE_PRIVATE)

    fun saveToken(authData: Auth?) {
        val editor = preferences.edit()

        authData?.let {
            editor.putString(Constants.TOKEN_KEY, it.token).apply()
            editor.putString(Constants.USER_ID_KEY, it.id).apply()
            editor.putBoolean(Constants.IS_LOGGED_IN_KEY, true).apply()
            return
        }
    }

    fun getToken(): String? {
        return preferences.getString(Constants.TOKEN_KEY, null)
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(Constants.IS_LOGGED_IN_KEY, false)
    }

    fun getUserId(): String? {
        return preferences.getString(Constants.USER_ID_KEY, null)
    }

    fun logout() {
        val editor = preferences.edit()
        editor.remove(Constants.TOKEN_KEY).apply()
        editor.remove(Constants.USER_ID_KEY).apply()
        editor.remove(Constants.IS_LOGGED_IN_KEY).apply()
    }
}