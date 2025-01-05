package com.paraskcd.unitedsetups.presentation.main.screens.Profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.paraskcd.unitedsetups.presentation.components.AlertDialogComponent
import com.paraskcd.unitedsetups.presentation.components.PostItem
import com.paraskcd.unitedsetups.presentation.components.PostSkeleton
import com.paraskcd.unitedsetups.presentation.components.ProfileHeader
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController, userId: String? = null, signout: () -> Unit, viewModel: ProfileViewModel) {
    var user by remember { viewModel.user }
    var posts by remember { viewModel.posts }
    var loading by remember { viewModel.loading }
    var stopFetching by remember { viewModel.stopFetching }
    val postIdLoading by remember { viewModel.postIdLoading }
    val pullRefreshState = rememberPullToRefreshState()
    val localContext = LocalContext.current
    var openAlertDialog by remember { mutableStateOf(false) }
    var deletePostId by remember { mutableStateOf("") }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize(),
        isRefreshing = loading,
        state = pullRefreshState,
        onRefresh = {
            viewModel.startFetching()
            if (user != null) {
                viewModel.getPostsByUserId(user!!.id)
            }
        },
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = loading,
                state = pullRefreshState,
                containerColor = DarkColorScheme.surface,
                color = DarkColorScheme.primary
            )
        }
    ) {
        if (openAlertDialog == true) {
            AlertDialogComponent(
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = { viewModel.deletePost(deletePostId) },
                dialogTitle = "Delete Post",
                dialogText = "Are you sure you want to delete this post?",
                icon = Icons.Outlined.DeleteForever
            )
        }
        LazyColumn {
            item {
                ProfileHeader(
                    user,
                    showSignout = userId == null,
                    signout = signout
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            if (loading) {
                item {
                    PostSkeleton()
                    PostSkeleton()
                }
            }
            items(posts) { post ->
                if (user != null) {
                    PostItem(
                        post,
                        user!!.id,
                        navController,
                        postIdLoading,
                        { postId, isLiked -> viewModel.likePost(postId, isLiked) },
                        { postId -> startActivity(localContext, viewModel.share(postId), null)},
                        { postId ->
                            deletePostId = postId
                            openAlertDialog = true
                        }
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                LaunchedEffect(true) {
                    if (!stopFetching) {
                        viewModel.fetchMorePostsByUserId()
                    }
                }
            }
        }
    }
}