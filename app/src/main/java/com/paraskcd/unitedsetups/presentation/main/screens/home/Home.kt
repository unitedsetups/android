package com.paraskcd.unitedsetups.presentation.main.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.paraskcd.unitedsetups.presentation.components.PostItem
import com.paraskcd.unitedsetups.presentation.components.PostSkeleton
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(modifier: Modifier = Modifier, navController: NavHostController, viewModel: HomeViewModel) {
    val posts = viewModel.posts.observeAsState(emptyList()).value
    val loading by remember { viewModel.loading }
    val stopFetching by remember { viewModel.stopFetching }
    val loggedInUserId by remember { viewModel.loggedInUserId }
    val listState = rememberLazyListState(
        viewModel.scrollIndex.value,
        viewModel.scrollOffset.value
    )
    val pullRefreshState = rememberPullToRefreshState()

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
        onRefresh = { viewModel.fetchPosts() },
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
        LazyColumn(
            state = listState
        ) {
            if (loading) {
                item {
                    PostSkeleton()
                    PostSkeleton()
                }
            }
            items(posts) { post ->
                PostItem(post, loggedInUserId, navController)
            }
            item {
                LaunchedEffect(true) {
                    if (!stopFetching) {
                        viewModel.fetchMorePosts()
                    }
                }
            }
        }
    }
}