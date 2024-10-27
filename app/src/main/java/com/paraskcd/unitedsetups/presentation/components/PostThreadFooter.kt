package com.paraskcd.unitedsetups.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.domain.model.PostThread
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme
import org.ocpsoft.prettytime.PrettyTime

@Composable
fun PostThreadFooter(postThread: PostThread, postData: Post, likePost: (String, Boolean) -> Unit, postThreadIdLoading: String?, activateReply: (PostThread) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (postThread.id == postThreadIdLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(4.dp))
        }
        else {
            IconButton({ likePost(postThread.id, true) }) {
                if (postThread.liked) {
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
            }
            Text(postThread.upvotes.toString())
            Spacer(modifier = Modifier.width(8.dp))
            IconButton({ likePost(postThread.id, false) }) {
                if (postThread.disliked) {
                    Icon(
                        imageVector = Icons.Filled.ThumbDown,
                        contentDescription = "Like",
                        tint = DarkColorScheme.primary
                    )
                }
                else {
                    Icon(
                        imageVector = Icons.Outlined.ThumbDown,
                        contentDescription = "Like",
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton({ activateReply(postThread) }) {
                Icon(
                    imageVector = Icons.Outlined.Forum,
                    contentDescription = "More",
                )
            }
        }
    }
    Text(
        text = PrettyTime().format(postThread.createdDateTime),
        fontSize = MaterialTheme.typography.labelSmall.fontSize,
        color = Color.White.copy(alpha = 0.5f)
    )
    if (postData.postThreads.indexOf(postThread) != postData.postThreads.size - 1) {
        HorizontalDivider()
    }
}