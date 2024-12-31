package com.paraskcd.unitedsetups.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme
import org.ocpsoft.prettytime.PrettyTime

@Composable
fun PostFooter(post: Post, likePost: (String, Boolean) -> Unit, postIdLoading: String?, navController: NavHostController, sharePost: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Row(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 8.dp)
            ) {
                if (post.id == postIdLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(4.dp))
                }
                else {
                    Button(
                        onClick = { likePost(post.id, true) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (post.liked) {
                                Icon(
                                    imageVector = Icons.Filled.ThumbUp,
                                    contentDescription = "Like",
                                    tint = DarkColorScheme.primary
                                )
                            }
                            else {
                                Icon(
                                    imageVector = Icons.Outlined.ThumbUp,
                                    contentDescription = "Like",
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(post.upvotes.toString())
                        }
                    }
                    VerticalDivider()
                    Button(
                        onClick = { likePost(post.id, false) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        )
                    ) {
                        Row {
                            if (post.disliked) {
                                Icon(
                                    imageVector = Icons.Filled.ThumbDown,
                                    contentDescription = "Dislike",
                                    tint = DarkColorScheme.primary
                                )
                            }
                            else {
                                Icon(
                                    imageVector = Icons.Outlined.ThumbDown,
                                    contentDescription = "Dislike",
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { navController.navigate("Post/${post.id}") },
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Forum,
                    contentDescription = "More",
                )
            }
        }
        IconButton(
            onClick = { sharePost(post.id) },
            modifier = Modifier
                .background(
                    color = Color.White.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "More",
            )
        }
    }
    Text(
        text = PrettyTime().format(post.createdDateTime),
        fontSize = MaterialTheme.typography.labelSmall.fontSize,
        color = Color.White.copy(alpha = 0.5f)
    )
}