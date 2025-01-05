package com.paraskcd.unitedsetups.presentation.main.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.paraskcd.unitedsetups.presentation.components.AlertDialogComponent
import com.paraskcd.unitedsetups.presentation.components.PostItem
import com.paraskcd.unitedsetups.presentation.components.PostSkeleton
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(modifier: Modifier = Modifier, navController: NavHostController, viewModel: HomeViewModel) {
    val posts by remember { viewModel.posts }
    val loading by remember { viewModel.loading }
    val stopFetching by remember { viewModel.stopFetching }
    val loggedInUserId by remember { viewModel.loggedInUserId }
    val listState = rememberLazyListState(
        viewModel.scrollIndex.value,
        viewModel.scrollOffset.value
    )
    val pullRefreshState = rememberPullToRefreshState()
    val postIdLoading by remember { viewModel.postIdLoading }
    val localContext = LocalContext.current
    var openAlertDialog by remember { mutableStateOf(false) }
    var deletePostId by remember { mutableStateOf("") }

    // after each scroll, update values in ViewModel
    LaunchedEffect(key1 = listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            viewModel.scrollIndex.value = listState.firstVisibleItemIndex
            viewModel.scrollOffset.value = listState.firstVisibleItemScrollOffset
        }
    }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize(),
        isRefreshing = loading,
        state = pullRefreshState,
        onRefresh = {
            viewModel.startFetching()
            viewModel.fetchPosts()
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
        LazyColumn(
            state = listState
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                if (loading) {
                    PostSkeleton()
                }
            }
            items(posts) { post ->
                PostItem(
                    post,
                    loggedInUserId,
                    navController,
                    postIdLoading,
                    { postId, isLiked -> viewModel.likePost(postId, isLiked) },
                    { postId -> startActivity(localContext, viewModel.share(postId), null) },
                    { postId ->
                        deletePostId = postId
                        openAlertDialog = true
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                LaunchedEffect(true) {
                    if (!stopFetching) {
                        viewModel.fetchMorePosts()
                    }
                }
            }
        }
    }
}