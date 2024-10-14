package com.paraskcd.unitedsetups.presentation.main.screens.Profile

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@Composable
fun Profile(navController: NavHostController) {
    val viewModel: ProfileViewModel = hiltViewModel()
    var user by remember { viewModel.user }
    var posts by remember { viewModel.posts }
    var loading by remember { viewModel.loading }
    var showShimmerCover by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getPostsByUserId(null)
        Log.d("User", user.toString())
    }

    LazyColumn {
        item {
            Box(contentAlignment = Alignment.BottomCenter) {
                AsyncImage(
                    model = Uri.parse("${Constants.BASE_URL}/${user?.coverImageUrl}"),
                    contentDescription = "Cover Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .background(shimmerBrush(showShimmer = showShimmerCover, targetValue = 1300f)),
                    onSuccess = { showShimmerCover = false },
                    onLoading = { showShimmerCover = true }
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(DarkColorScheme.surface, Color.Transparent)
                        )
                    )
                )
                Column(modifier = Modifier
                    .offset(y = 100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = Uri.parse("${Constants.BASE_URL}/${user?.profileImageUrl}"),
                        contentDescription = "Cover Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                            .clip(CircleShape)
                            .background(shimmerBrush(showShimmer = showShimmerCover, targetValue = 1300f))
                            .shadow(elevation = 16.dp),
                        onSuccess = { showShimmerCover = false },
                        onLoading = { showShimmerCover = true }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(user?.name ?: "", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(120.dp))
        }
        if (loading) {
            item {
                PostSkeleton()
                PostSkeleton()
            }
        }
        items(posts) { post ->
            if (user != null) {
                PostItem(post, user!!.id, navController)
            }
        }
    }
}