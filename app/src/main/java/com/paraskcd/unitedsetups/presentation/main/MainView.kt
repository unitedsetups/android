package com.paraskcd.unitedsetups.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.paraskcd.unitedsetups.presentation.main.screens.Home
import com.paraskcd.unitedsetups.presentation.main.screens.NewPost

@Composable
fun MainView(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(navController, startDestination = "Home", modifier) {
        composable("Home") {
            Home()
        }
        composable("NewPost") {
            NewPost(navController)
        }
    }
}