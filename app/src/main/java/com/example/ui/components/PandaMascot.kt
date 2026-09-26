package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBg
import com.example.ui.theme.JapanRed
import com.example.ui.theme.JapanRedLight
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900

@Composable
fun PandaAvatar(
    size: Dp = 64.dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "panda_bob")
    val bobOffset by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "panda_bob_offset"
    )

    Canvas(
        modifier = modifier
            .size(size)
            .offset(y = bobOffset.dp)
            .testTag("panda_mascot_avatar")
    ) {
        val w = this.size.width
        val h = this.size.height

        // Outer soft glow
        drawCircle(
            color = Color(0xFFFFF1F2),
            radius = w * 0.48f,
            center = Offset(w * 0.5f, h * 0.5f)
        )

        // Panda Left Ear
        drawCircle(
            color = Color(0xFF1E293B),
            radius = w * 0.16f,
            center = Offset(w * 0.28f, h * 0.26f)
        )
        // Left Ear inner
        drawCircle(
            color = Color(0xFF475569),
            radius = w * 0.08f,
            center = Offset(w * 0.28f, h * 0.26f)
        )

        // Panda Right Ear
        drawCircle(
            color = Color(0xFF1E293B),
            radius = w * 0.16f,
            center = Offset(w * 0.72f, h * 0.26f)
        )
        // Right Ear inner
        drawCircle(
            color = Color(0xFF475569),
            radius = w * 0.08f,
            center = Offset(w * 0.72f, h * 0.26f)
        )

        // Panda Face / Head
        drawCircle(
            color = Color.White,
            radius = w * 0.38f,
            center = Offset(w * 0.5f, h * 0.54f)
        )
        // Face outline
        drawCircle(
            color = Color(0xFFE2E8F0),
            radius = w * 0.38f,
            center = Offset(w * 0.5f, h * 0.54f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = w * 0.03f)
        )

        // Eye Patches (Dark slate ovals)
        drawOval(
            color = Color(0xFF1E293B),
            topLeft = Offset(w * 0.28f, h * 0.44f),
            size = Size(w * 0.16f, h * 0.20f)
        )
        drawOval(
            color = Color(0xFF1E293B),
            topLeft = Offset(w * 0.56f, h * 0.44f),
            size = Size(w * 0.16f, h * 0.20f)
        )

        // Eye pupils (white sparkles)
        drawCircle(
            color = Color.White,
            radius = w * 0.04f,
            center = Offset(w * 0.36f, h * 0.51f)
        )
        drawCircle(
            color = Color.White,
            radius = w * 0.04f,
            center = Offset(w * 0.64f, h * 0.51f)
        )

        // Pink Cheeks
        drawCircle(
            color = Color(0xFFFFB6C1),
            radius = w * 0.07f,
            center = Offset(w * 0.24f, h * 0.62f)
        )
        drawCircle(
            color = Color(0xFFFFB6C1),
            radius = w * 0.07f,
            center = Offset(w * 0.76f, h * 0.62f)
        )

        // Nose (small black triangle/oval)
        drawOval(
            color = Color(0xFF1E293B),
            topLeft = Offset(w * 0.46f, h * 0.59f),
            size = Size(w * 0.08f, h * 0.06f)
        )

        // Smiling mouth
        val mouthPath = Path().apply {
            moveTo(w * 0.45f, h * 0.67f)
            quadraticTo(w * 0.50f, h * 0.71f, w * 0.55f, h * 0.67f)
        }
        drawPath(
            path = mouthPath,
            color = Color(0xFF1E293B),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = w * 0.03f)
        )

        // Sakura flower on right ear / head
        val flowerCenter = Offset(w * 0.68f, h * 0.24f)
        val petalRadius = w * 0.06f
        val flowerColor = Color(0xFFFF69B4) // Bright pink
        for (i in 0 until 5) {
            val angle = (i * 72) * (Math.PI / 180.0)
            val px = flowerCenter.x + (petalRadius * 1.1 * Math.cos(angle)).toFloat()
            val py = flowerCenter.y + (petalRadius * 1.1 * Math.sin(angle)).toFloat()
            drawCircle(color = flowerColor, radius = petalRadius, center = Offset(px, py))
        }
        // Center of flower (golden yellow)
        drawCircle(color = Color(0xFFFFD700), radius = petalRadius * 0.7f, center = flowerCenter)
    }
}

@Composable
fun PandaSpeechBanner(
    text: String = "চলো আজ নতুন কিছু শিখি! 🐼",
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            .testTag("panda_speech_banner")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PandaAvatar(size = 58.dp)
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(JapanRedLight, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun PandaEmptyState(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PandaAvatar(size = 80.dp)
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Slate900
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = Slate700,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
