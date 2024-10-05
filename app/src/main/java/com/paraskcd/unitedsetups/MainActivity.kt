package com.paraskcd.unitedsetups

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.paraskcd.unitedsetups.presentation.authentication.AuthenticationView
import com.paraskcd.unitedsetups.ui.theme.UnitedSetupsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainActivityViewModel: MainActivityViewModel = hiltViewModel()

            UnitedSetupsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (mainActivityViewModel.isLoggedIn()) {

                    } else {
                        AuthenticationView(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}