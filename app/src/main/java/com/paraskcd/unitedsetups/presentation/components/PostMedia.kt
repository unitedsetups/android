package com.paraskcd.unitedsetups.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush

@Composable
fun PostMedia(post: Post) {
    val pagerState = rememberPagerState(pageCount = { post.postMediaUrls.count() })
    var showShimmer by remember { mutableStateOf(false) }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.padding(16.dp)
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
                .height(256.dp)
                .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = Uri.parse("${Constants.BASE_URL}/${post.postMediaUrls[page].thumbnailPath}"),
                contentDescription = post.text,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radius = 16.dp)
                    .background(shimmerBrush(showShimmer = showShimmer, targetValue = 1300f)),
                onSuccess = { showShimmer = false },
                onLoading = { showShimmer = true },
            )
            AsyncImage(
                model = Uri.parse("${Constants.BASE_URL}/${post.postMediaUrls[page].thumbnailPath}"),
                contentDescription = post.text,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(256.dp)
                    .height(256.dp)
                    .background(shimmerBrush(showShimmer = showShimmer, targetValue = 1300f)),
                onSuccess = { showShimmer = false },
                onLoading = { showShimmer = true },
            )
        }
    }
}