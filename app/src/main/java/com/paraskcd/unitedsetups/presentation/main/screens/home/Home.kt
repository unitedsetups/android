package com.paraskcd.unitedsetups.presentation.main.screens.home

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.paraskcd.unitedsetups.presentation.components.PostItem
import com.paraskcd.unitedsetups.presentation.components.PostSkeleton

@Composable
fun Home(modifier: Modifier = Modifier) {
    val viewModel: HomeViewModel = hiltViewModel()
    val posts = viewModel.posts.observeAsState(emptyList()).value
    val loading by remember { viewModel.loading }

    LaunchedEffect(viewModel) {
        viewModel.fetchPosts()
    }

    LazyColumn(modifier = modifier) {
        if (loading) {
            item {
                PostSkeleton()
                PostSkeleton()
            }
        }
        items(posts) { post ->
            PostItem(post)
        }
    }
}