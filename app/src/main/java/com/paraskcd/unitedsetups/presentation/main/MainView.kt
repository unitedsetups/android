package com.paraskcd.unitedsetups.presentation.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.paraskcd.unitedsetups.presentation.main.screens.NewPost.NewPost
import com.paraskcd.unitedsetups.presentation.main.screens.Post.Post
import com.paraskcd.unitedsetups.presentation.main.screens.Post.PostViewModel
import com.paraskcd.unitedsetups.presentation.main.screens.PostImage.PostImage
import com.paraskcd.unitedsetups.presentation.main.screens.Profile.Profile
import com.paraskcd.unitedsetups.presentation.main.screens.home.Home
import com.paraskcd.unitedsetups.presentation.main.screens.home.HomeViewModel

@Composable
fun MainView(modifier: Modifier = Modifier, navController: NavHostController, homeViewModel: HomeViewModel, postViewModel: PostViewModel, signout: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = "Home",
        modifier = modifier,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) }
    ) {
        composable(route = "Home") {
            Home(navController = navController, viewModel = homeViewModel)
        }
        composable(route = "Profile") {
            Profile(navController = navController, signout = signout)
        }
        composable(route = "Profile/{userId}") { backstackEntry ->
            var userId = backstackEntry.arguments?.getString("userId")
            if (homeViewModel.loggedInUserId.equals(userId)) {
                navController.navigate("Profile") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            userId?.let {
                Profile(navController = navController, userId = userId, signout)
            }
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
        composable("Post/{postId}") { navBackStackEntry ->
            val postId = navBackStackEntry.arguments?.getString("postId")
            postId?.let {
                Post(postId = postId, navController = navController, viewModel = postViewModel)
            }
        }
    }
}