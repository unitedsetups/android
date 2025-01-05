package com.paraskcd.unitedsetups.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@Composable
fun PostItem(
    post: Post,
    loggedInUserId: String?,
    navController: NavHostController,
    postIdLoading: String?,
    likePost: (String, Boolean) -> Unit,
    sharePost: (String) -> Unit,
    deletePost: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .shadow(elevation = 16.dp)
            .background(
                DarkColorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                navController.navigate("Post/${post.id}")
            }
    ) {
        PostHeader(post, loggedInUserId, navController, deletePost)
        PostMedia(post, navController)
        PostFooter(post, likePost, postIdLoading, navController, sharePost)
    }
}