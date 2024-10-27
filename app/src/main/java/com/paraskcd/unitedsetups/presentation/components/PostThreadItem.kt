package com.paraskcd.unitedsetups.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.domain.model.PostThread
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@Composable
fun PostThreadItem(
    postThread: PostThread,
    postData: Post,
    navController:
    NavHostController,
    currentRoute: String,
    postThreadIdLoading: String?,
    isChild: Boolean,
    likePostThread: (String, Boolean) -> Unit,
    activateReply: (String) -> Unit
) {
    fun getShape(): Shape {
        if (postData.postThreads.size == 1) {
            return RoundedCornerShape(16.dp)
        }
        if (postData.postThreads.size == 2) {
            if (postData.postThreads.indexOf(postThread) == 0) {
                return RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            }
            return RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
        } else {
            if (postData.postThreads.indexOf(postThread) == 0) {
                return RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            }
            if (postData.postThreads.indexOf(postThread) == postData.postThreads.size - 1) {
                return RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            }
        }

        return RoundedCornerShape(0.dp)
    }

    fun getExtraPadding(): Dp {
        if (isChild) {
            return 16.dp
        } else {
            return 0.dp
        }
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .shadow(elevation = 16.dp)
            .background(
                DarkColorScheme.surface,
                shape = getShape()
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(start = getExtraPadding())
            .drawBehind {
                if (isChild) {
                    val strokeWidth = 1 * density
                    //Draw line function for left border
                    drawLine(
                        Color.DarkGray,
                        Offset(0f, strokeWidth),
                        Offset(0f, size.height),
                        strokeWidth
                    )
                }
            }
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            PostThreadHeader(postThread, navController, currentRoute)
            PostThreadMedia(postThread)
            PostThreadFooter(postThread, postData, likePostThread, postThreadIdLoading, activateReply)
        }

    }
}