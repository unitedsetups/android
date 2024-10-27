package com.paraskcd.unitedsetups.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.domain.model.PostThread
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush

@Composable
fun PostThreadMedia(postThread: PostThread) {
    if (postThread.postMediaUrls.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = { postThread.postMediaUrls.count() })
        var showShimmer by remember { mutableStateOf(false) }

        Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.BottomCenter) {
            HorizontalPager(
                state = pagerState,
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = Uri.parse("${Constants.BASE_URL}/${postThread.postMediaUrls[page].thumbnailPath}"),
                        contentDescription = postThread.text,
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
                        model = Uri.parse("${Constants.BASE_URL}/${postThread.postMediaUrls[page].thumbnailPath}"),
                        contentDescription = postThread.text,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(256.dp)
                            .background(shimmerBrush(showShimmer = showShimmer, targetValue = 1300f)),
                        onSuccess = { showShimmer = false },
                        onLoading = { showShimmer = true },
                    )
                }
            }
            if (postThread.postMediaUrls.count() > 1) {
                Row(modifier = Modifier.padding(8.dp)) {
                    (0..(postThread.postMediaUrls.count() - 1)).forEach { index ->
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