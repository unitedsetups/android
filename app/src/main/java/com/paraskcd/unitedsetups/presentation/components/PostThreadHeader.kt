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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.paraskcd.unitedsetups.domain.model.PostThread
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@Composable
fun PostThreadHeader(postThread: PostThread, navController: NavHostController, currentRoute: String) {
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
                        navController.navigate("Profile/${postThread.postedBy.id}") {
                            popUpTo(currentRoute) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            ) {
                if (postThread.postedBy.profileImageThumbnailUrl?.isNotEmpty() == true) {
                    AsyncImage(
                        model = Uri.parse("${Constants.BASE_URL}/${postThread.postedBy.profileImageThumbnailUrl}"),
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
                    text = postThread.postedBy.name,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "@${postThread.postedBy.username}",
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
        Text(postThread.text)
    }
}