package com.paraskcd.unitedsetups.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.paraskcd.unitedsetups.presentation.brushes.shimmerBrush
import com.paraskcd.unitedsetups.ui.theme.DarkColorScheme

@Composable
fun PostSkeleton() {
    var shimmer = shimmerBrush(showShimmer = true, targetValue = 1300f)
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
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(shimmer)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .background(shimmer, shape = RoundedCornerShape(16.dp))
                        .padding(8.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(30.dp)
                .background(shimmer, shape = RoundedCornerShape(corner = CornerSize(16.dp))),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(256.dp)
                .background(shimmer, shape = RoundedCornerShape(corner = CornerSize(16.dp))),
        )
        Box(
            modifier = Modifier.fillMaxWidth().background(shimmer, shape = RoundedCornerShape(16.dp)).padding(25.dp)
        )
        Box(
            modifier = Modifier.padding(8.dp).width(120.dp).background(shimmer, shape = RoundedCornerShape(16.dp)).padding(8.dp)
        )
    }
}