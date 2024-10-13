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
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
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
import com.paraskcd.unitedsetups.domain.model.Post
import org.ocpsoft.prettytime.PrettyTime

@Composable
fun PostFooter(post: Post) {
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
                IconButton(onClick = { /* TODO */ }) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.ThumbUp,
                            contentDescription = "More",
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(post.upvotes.toString())
                    }
                }
                VerticalDivider()
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Icons.Outlined.ThumbDown,
                        contentDescription = "More",
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { /* TODO */ },
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
            onClick = { /* TODO */ },
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