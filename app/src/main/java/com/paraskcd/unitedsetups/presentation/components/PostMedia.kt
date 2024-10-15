package com.paraskcd.unitedsetups.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush

@Composable
fun PostMedia(post: Post, navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { post.postMediaUrls.count() })
    var showShimmer by remember { mutableStateOf(false) }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteId by remember { derivedStateOf { currentBackStackEntry?.destination?.id ?: 0 } }

    Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                    .clickable {
                        navController.navigate("PostImage/${post.id}") {
                            popUpTo(currentRouteId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = Uri.parse("${Constants.BASE_URL}/${post.postMediaUrls[page].thumbnailPath}"),
                    contentDescription = post.text,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(radius = 16.dp)
                        .alpha(0.25f)
                        .background(shimmerBrush(showShimmer = showShimmer, targetValue = 1300f)),
                    onSuccess = { showShimmer = false },
                    onLoading = { showShimmer = true },
                )
                AsyncImage(
                    model = Uri.parse("${Constants.BASE_URL}/${post.postMediaUrls[page].thumbnailPath}"),
                    contentDescription = post.text,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(256.dp)
                        .background(shimmerBrush(showShimmer = showShimmer, targetValue = 1300f)),
                    onSuccess = { showShimmer = false },
                    onLoading = { showShimmer = true },
                )
            }
        }
        if (post.postMediaUrls.count() > 1) {
            Row(modifier = Modifier.padding(8.dp)) {
                (0..(post.postMediaUrls.count() - 1)).forEach { index ->
                    val color = if (pagerState.currentPage == index) {
                        Color.White.copy(alpha = 1f)
                    } else {
                        Color.White.copy(alpha = 0.5f)
                    }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .background(color, shape = CircleShape)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}