package com.paraskcd.unitedsetups.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DevicesOther
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.domain.model.User
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@Composable
fun ProfileHeader(user: User?, showSignout: Boolean = false, signout: () -> Unit) {
    var showShimmerCover by remember { mutableStateOf(true) }
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .fillMaxWidth()
            .background(DarkColorScheme.surface, shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .padding(bottom = 150.dp)
    ) {
        if (user?.coverImageUrl?.isNotEmpty() == true) {
            AsyncImage(
                model = Uri.parse("${Constants.BASE_URL}/${user.coverImageUrl}"),
                contentDescription = "Cover Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(shimmerBrush(showShimmer = showShimmerCover, targetValue = 1300f)),
                onSuccess = { showShimmerCover = false },
                onLoading = { showShimmerCover = true }
            )
        } else {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(DarkColorScheme.background, shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DarkColorScheme.surface, Color.Transparent)
                )
            ),
            contentAlignment = Alignment.TopEnd
        ) {
            if (showSignout) {
                Column(
                    modifier = Modifier.padding(end = 16.dp, top = 16.dp),
                    horizontalAlignment = Alignment.End
                ) {
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.Edit,
                                        contentDescription = "Edit Profile",
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Edit Profile")
                                }
                            },
                            onClick = { },
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Outlined.Logout,
                                        contentDescription = "Logout",
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Logout")
                                }
                            },
                            onClick = { signout() },
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier
            .offset(y = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (user?.profileImageUrl?.isNotEmpty() == true) {
                AsyncImage(
                    model = Uri.parse("${Constants.BASE_URL}/${user?.profileImageUrl}"),
                    contentDescription = "Cover Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                        .shadow(elevation = if (!showShimmerCover) 16.dp else 0.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(
                            shimmerBrush(showShimmer = showShimmerCover, targetValue = 1300f),
                            shape = CircleShape
                        ),
                    onSuccess = { showShimmerCover = false },
                    onLoading = { showShimmerCover = true }
                )
            } else {
                Box(modifier = Modifier
                    .size(150.dp)
                    .shadow(elevation = 16.dp, shape = CircleShape)
                    .background(DarkColorScheme.background, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile Icon",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(120.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                user?.name ?: "",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
            )
        }
    }
}