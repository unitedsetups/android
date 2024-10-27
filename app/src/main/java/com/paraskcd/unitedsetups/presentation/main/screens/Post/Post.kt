package com.paraskcd.unitedsetups.presentation.main.screens.Post

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.paraskcd.unitedsetups.presentation.components.PostItem
import com.paraskcd.unitedsetups.presentation.components.PostSkeleton
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.content.ContextCompat.startActivity
import com.paraskcd.unitedsetups.presentation.components.PostThreadItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post(postId: String, navController: NavHostController, viewModel: PostViewModel) {
    var post by remember { viewModel.post }
    var loading by remember { viewModel.loading }
    var loggedInUserId by remember { viewModel.loggedInUserId }
    var postIdLoading by remember { viewModel.postIdLoading }
    var postThreadIdLoading by remember { viewModel.postThreadIdLoading }
    var selectedImages by remember { viewModel.selectedImages }
    val pullRefreshState = rememberPullToRefreshState()
    val composableScope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember { derivedStateOf { currentBackStackEntry?.destination?.route ?: "Home" } }
    val localDensity = LocalDensity.current
    val localContext = LocalContext.current
    val replyPostThread by remember { viewModel.replyParentPostThread }

    LaunchedEffect(postId) {
        viewModel.getPostById(postId)
    }

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5),
        onResult = { uris -> selectedImages = uris }
    )

    fun launchPhotoPicker() {
        multiplePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    fun createNewPostThread() {
        composableScope.launch {
            viewModel.createPostThread()
        }
    }

    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }

    LaunchedEffect(columnHeightDp) {
        Log.d("ColumnHeight", "Column height: $columnHeightDp")
    }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize(),
        isRefreshing = loading,
        state = pullRefreshState,
        onRefresh = {
            viewModel.getPostById(postId)
        },
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = loading,
                state = pullRefreshState,
                containerColor = DarkColorScheme.surface,
                color = DarkColorScheme.primary
            )
        },
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                if (loading) {
                    PostSkeleton()
                }
            }
            post?.let { postData ->
                item {
                    PostItem(
                        postData,
                        loggedInUserId,
                        navController,
                        postIdLoading,
                        { _, isLiked -> viewModel.likePost(isLiked) },
                        { startActivity(localContext, viewModel.share(), null) }
                    )
                }
                items(postData.postThreads) { postThread ->
                    PostThreadItem(
                        postThread,
                        postData,
                        navController,
                        currentRoute,
                        postThreadIdLoading,
                        false,
                        { postThreadId, isLiked -> viewModel.likePostThread(isLiked, postThreadId) },
                        { postThread -> viewModel.getReplyPostThread(postThread) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(columnHeightDp.plus(16.dp)))
                }
            }
        }
        AnimatedVisibility(
            visible = currentRoute.contains("Post"),
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(DarkColorScheme.surface, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .onGloballyPositioned { coordinates ->
                        columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                    }
                    .safeDrawingPadding()
            ) {
                replyPostThread?.let { postThread ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.05f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(imageVector = Icons.Outlined.Forum, contentDescription = "Image")
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Reply to:")
                                Text("${postThread.postedBy.username}: ${postThread.text}")
                            }
                        }
                        IconButton(onClick = { viewModel.replyParentPostThread.value = null }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Image")
                        }
                    }
                }
                TextField(
                    value = viewModel.text,
                    onValueChange = { text -> viewModel.updateText(text) },
                    label = { Text("What's on your mind?") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = DarkColorScheme.error
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkColorScheme.surface),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { launchPhotoPicker() }, modifier = Modifier.padding(start = 16.dp)) {
                        Icon(imageVector = Icons.Filled.Image, contentDescription = "Image")
                    }
                    IconButton(onClick = { createNewPostThread() }, modifier = Modifier.padding(end = 16.dp)) {
                        Icon(imageVector = Icons.Filled.Send, contentDescription = "Send")
                    }
                }
            }
        }

    }
}