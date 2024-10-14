package com.paraskcd.unitedsetups.presentation.main.screens.PostImage

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostImage(postId: String, navController: NavHostController) {
    val viewModel: PostImageViewModel = hiltViewModel()
    var post by remember { viewModel.post }
    var loading by remember { viewModel.loading }

    LaunchedEffect(viewModel) {
        viewModel.getPostById(postId)
    }

    if (loading) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(DarkColorScheme.background), contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    color = Color.White
                )
                Text(
                    text = "Loading...",
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }
        }
    }
    else {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(DarkColorScheme.background)
            ) {
                if (post != null) {
                    var showShimmer by remember { mutableStateOf(false) }
                    val pagerState = rememberPagerState(pageCount = { post!!.postMediaUrls.count() })
                    val contrast = 1f // 0f..10f (1 should be default)
                    val brightness = -180f // -255f..255f (0 should be default)
                    val colorMatrix = floatArrayOf(
                        contrast, 0f, 0f, 0f, brightness,
                        0f, contrast, 0f, 0f, brightness,
                        0f, 0f, contrast, 0f, brightness,
                        0f, 0f, 0f, 1f, 0f
                    )
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                        HorizontalPager(state = pagerState) { page ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = Uri.parse("${Constants.BASE_URL}/${post!!.postMediaUrls[page].thumbnailPath}"),
                                    contentDescription = post!!.text,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .blur(radius = 16.dp)
                                        .alpha(0.25f)
                                        .background(
                                            shimmerBrush(
                                                showShimmer = showShimmer,
                                                targetValue = 1300f
                                            )
                                        ),
                                    onSuccess = { showShimmer = false },
                                    onLoading = { showShimmer = true },
                                    colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
                                )
                                AsyncImage(
                                    model = Uri.parse("${Constants.BASE_URL}/${post!!.postMediaUrls[page].path}"),
                                    contentDescription = post!!.text,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            shimmerBrush(
                                                showShimmer = showShimmer,
                                                targetValue = 1300f
                                            )
                                        ),
                                    onSuccess = { showShimmer = false },
                                    onLoading = { showShimmer = true },
                                )
                            }
                        }
                        if (post!!.postMediaUrls.count() > 1) {
                            Row(modifier = Modifier.padding(8.dp)) {
                                (0..(post!!.postMediaUrls.count() - 1)).forEach { index ->
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
            }
        }
    }
}