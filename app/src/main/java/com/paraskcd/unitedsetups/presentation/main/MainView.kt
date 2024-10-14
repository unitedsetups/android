package com.paraskcd.unitedsetups.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.paraskcd.unitedsetups.presentation.main.screens.home.Home
import com.paraskcd.unitedsetups.presentation.main.screens.NewPost.NewPost
import com.paraskcd.unitedsetups.presentation.main.screens.PostImage.PostImage
import com.paraskcd.unitedsetups.presentation.main.screens.home.HomeViewModel

@Composable
fun MainView(modifier: Modifier = Modifier, navController: NavHostController, homeViewModel: HomeViewModel) {
    NavHost(navController, startDestination = "Home", modifier) {
        composable("Home") {
            Home(navController = navController, viewModel = homeViewModel)
        }
        composable("NewPost") {
            NewPost(navController = navController)
        }
        composable("PostImage/{postId}") { navBackStackEntry ->
            val postId = navBackStackEntry.arguments?.getString("postId")
            postId?.let {
                PostImage(postId = postId, navController = navController)
            }
        }
    }
}