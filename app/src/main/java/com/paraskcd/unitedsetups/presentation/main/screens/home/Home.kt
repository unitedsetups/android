package com.paraskcd.unitedsetups.presentation.main.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paraskcd.unitedsetups.presentation.components.PostItem
import com.paraskcd.unitedsetups.presentation.components.PostSkeleton

@Composable
fun Home(modifier: Modifier = Modifier, navController: NavHostController, viewModel: HomeViewModel) {
    val posts = viewModel.posts.observeAsState(emptyList()).value
    val loading by remember { viewModel.loading }
    val loggedInUserId by remember { viewModel.loggedInUserId }
    val listState = rememberLazyListState(
        viewModel.scrollIndex.value,
        viewModel.scrollOffset.value
    )

    // after each scroll, update values in ViewModel
    LaunchedEffect(key1 = listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            viewModel.scrollIndex.value = listState.firstVisibleItemIndex
            viewModel.scrollOffset.value = listState.firstVisibleItemScrollOffset
        }
    }

    LazyColumn(modifier = modifier, state = listState) {
        if (loading) {
            item {
                PostSkeleton()
                PostSkeleton()
            }
        }
        items(posts) { post ->
            PostItem(post, loggedInUserId, navController)
        }
    }
}