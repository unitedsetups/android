package com.paraskcd.unitedsetups.presentation.main.screens.Profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush
import com.paraskcd.unitedsetups.presentation.components.PostItem
import com.paraskcd.unitedsetups.presentation.components.PostSkeleton
import com.paraskcd.unitedsetups.presentation.components.ProfileHeader
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController, userId: String? = null) {
    val viewModel: ProfileViewModel = hiltViewModel()
    var user by remember { viewModel.user }
    var posts by remember { viewModel.posts }
    var loading by remember { viewModel.loading }
    var stopFetching by remember { viewModel.stopFetching }
    val postIdLoading by remember { viewModel.postIdLoading }
    val pullRefreshState = rememberPullToRefreshState()

    LaunchedEffect(viewModel) {
        if (userId == null) {
            viewModel.getMyProfile()
        }
    }

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
        LazyColumn {
            item {
                ProfileHeader(user)
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
                        postIdLoading
                    ) { postId, isLiked -> viewModel.likePost(postId, isLiked) }
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