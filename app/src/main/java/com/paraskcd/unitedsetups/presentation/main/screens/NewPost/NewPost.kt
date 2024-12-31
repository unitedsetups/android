package com.paraskcd.unitedsetups.presentation.main.screens.NewPost

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun NewPost(navController: NavHostController, modifier: Modifier = Modifier) {
    val newPostViewModel: NewPostViewModel = hiltViewModel()
    var postText by remember { newPostViewModel.postText }
    var selectedImages by remember { newPostViewModel.selectedImages }
    val pagerState = rememberPagerState(pageCount = { selectedImages.count() })
    val focusRequester = remember { FocusRequester() }
    val loading by remember { newPostViewModel.loading }
    val composableScope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember { derivedStateOf { currentBackStackEntry?.destination?.route ?: "Home" } }

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5),
        onResult = { uris -> selectedImages = uris }
    )

    fun launchPhotoPicker() {
        multiplePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    fun createNewPost() {
        composableScope.launch {
            val result = newPostViewModel.createPost()
            if (result != null) {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(focusRequester) {
        awaitFrame()
        focusRequester.requestFocus()
    }

    val window = (LocalView.current.context as Activity).window
    val navbarColor = DarkColorScheme.surface.toArgb()

    if (loading) {
        SideEffect {
            window.navigationBarColor = Color.Transparent.toArgb()
        }

        Box(modifier = Modifier.fillMaxSize().background(DarkColorScheme.background), contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    color = Color.White
                )
                Text(
                    text = "Adding your post...",
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }
        }
    } else {
        DisposableEffect(Unit) {
            window.navigationBarColor = navbarColor

            onDispose {
                window.navigationBarColor = Color.Transparent.toArgb()
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if (selectedImages.count() > 0) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                        HorizontalPager(state = pagerState) { page ->
                            Box(contentAlignment = Alignment.TopStart) {
                                AsyncImage(
                                    model = selectedImages[page],
                                    contentDescription = "Image $page",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(256.dp),
                                    contentScale = ContentScale.Crop
                                )
                                SmallFloatingActionButton(
                                    onClick = {
                                        selectedImages = selectedImages.filter { it != selectedImages[page] }
                                    },
                                    modifier = Modifier.padding(16.dp),
                                    containerColor = DarkColorScheme.primary
                                ) {
                                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Remove Image")
                                }
                            }
                        }
                        if (selectedImages.count() > 1) {
                            Row(modifier = Modifier.padding(8.dp)) {
                                (0..(selectedImages.count() - 1)).forEach { index ->
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
                TextField(
                    value = postText,
                    onValueChange = { postText = it },
                    label = { Text("What's on your mind?") },
                    modifier = Modifier
                        .padding(16.dp)
                        .focusRequester(focusRequester),
                    maxLines = 22,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
            }
            AnimatedVisibility(
                visible = currentRoute == "NewPost",
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkColorScheme.surface, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .imePadding(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { launchPhotoPicker() }, modifier = Modifier.padding(start = 16.dp)) {
                        Icon(imageVector = Icons.Filled.Image, contentDescription = "Image")
                    }
                    IconButton(onClick = { createNewPost()  }, modifier = Modifier.padding(end = 16.dp)) {
                        Icon(imageVector = Icons.Filled.Send, contentDescription = "Send")
                    }
                }
            }
        }
    }
}