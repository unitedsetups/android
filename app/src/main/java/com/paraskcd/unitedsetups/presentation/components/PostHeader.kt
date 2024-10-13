package com.paraskcd.unitedsetups.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@Composable
fun PostHeader(post: Post) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = Uri.parse("${Constants.BASE_URL}/${post.postedBy.profileImageThumbnailUrl}"),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(shimmerBrush())
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = post.postedBy.name,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "@${post.postedBy.username}",
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
            Column {
                IconButton (onClick = { menuExpanded = !menuExpanded }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More",
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    modifier = Modifier.background(DarkColorScheme.surface)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("Add Device")
                        },
                        onClick = { /* TODO */ },
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Add Setup Items")
                        },
                        onClick = { /* TODO */ },
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Edit Post")
                        },
                        onClick = { /* TODO */ },
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Delete",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = { /* TODO */ },
                    )
                }
            }
        }
        Text(
            text = post.text,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}