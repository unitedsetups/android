package com.paraskcd.unitedsetups.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.DevicesOther
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.PhonelinkSetup
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@Composable
fun PostHeader(post: Post, loggedInUserId: String?, navController: NavHostController, deletePost: (String) -> Unit) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember { derivedStateOf { currentBackStackEntry?.destination?.route ?: "" } }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    if (!currentRoute.contains("Profile")) {
                        navController.navigate("Profile/${post.postedBy.id}") {
                            popUpTo(currentRoute) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            ) {
                if (post.postedBy.profileImageThumbnailUrl?.isNotEmpty() == true) {
                    AsyncImage(
                        model = Uri.parse("${Constants.BASE_URL}/${post.postedBy.profileImageThumbnailUrl}"),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(shimmerBrush())
                    )
                } else {
                    Box(modifier = Modifier
                        .size(32.dp)
                        .background(DarkColorScheme.background, shape = CircleShape), contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile Icon",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
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
                    if (post.postedBy.id == loggedInUserId) {
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.DevicesOther,
                                        contentDescription = "Add Device",
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Add Device")
                                }
                            },
                            onClick = { /* TODO */ },
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.PhonelinkSetup,
                                        contentDescription = "Add Setup Items",
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Add Setup Items")
                                }
                            },
                            onClick = { /* TODO */ },
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.EditNote,
                                        contentDescription = "Edit Post",
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Edit Post")
                                }
                            },
                            onClick = { /* TODO */ },
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.DeleteForever,
                                        contentDescription = "Delete Post",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Delete Post",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            onClick = { deletePost(post.id) },
                        )
                    }
                    else {
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.Report,
                                        contentDescription = "Report",
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Report")
                                }
                            },
                            onClick = { /* TODO */ },
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.Bookmark,
                                        contentDescription = "Bookmark",
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Bookmark")
                                }
                            },
                            onClick = { /* TODO */ },
                        )
                    }
                }
            }
        }
        Text(
            text = post.text,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}