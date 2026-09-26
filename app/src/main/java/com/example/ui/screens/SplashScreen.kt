package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.JapanRed
import com.example.ui.theme.JapanRedDark
import com.example.ui.theme.LightBg
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate900
import kotlinx.coroutines.delay
import kotlin.random.Random

private data class SakuraPetal(
    val initialXRatio: Float,
    val speed: Float,
    val size: Float,
    val initialRotation: Float,
    val rotationSpeed: Float,
    val swayFrequency: Float,
    val color: Color
)

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val alphaAnim = remember { Animatable(0f) }
    val scaleAnim = remember { Animatable(0.85f) }
    var animationTime by remember { mutableFloatStateOf(0f) }

    val petals = remember {
        val random = Random(42)
        List(28) {
            SakuraPetal(
                initialXRatio = random.nextFloat(),
                speed = 0.15f + random.nextFloat() * 0.25f,
                size = 14f + random.nextFloat() * 16f,
                initialRotation = random.nextFloat() * 360f,
                rotationSpeed = (random.nextFloat() - 0.5f) * 120f,
                swayFrequency = 1.5f + random.nextFloat() * 2f,
                color = if (random.nextBoolean()) Color(0xFFFFB7C5) else Color(0xFFFF94A8)
            )
        }
    }

    // Timer for 2000ms duration
    LaunchedEffect(Unit) {
        alphaAnim.animateTo(1f, animationSpec = tween(700))
        scaleAnim.animateTo(1f, animationSpec = tween(700))
        delay(1300) // total ~2000ms
        onSplashFinished()
    }

    // Continuous petal flutter loop
    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis()
        while (true) {
            val elapsedSec = (System.currentTimeMillis() - startTime) / 1000f
            animationTime = elapsedSec
            delay(16)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
            .clickable { onSplashFinished() }
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        // Cherry Blossom Petals Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            petals.forEach { petal ->
                val progressY = (petal.speed * animationTime) % 1.2f
                val y = progressY * canvasH - 40f
                val sway = kotlin.math.sin((animationTime * petal.swayFrequency).toDouble()).toFloat() * 35f
                val x = (petal.initialXRatio * canvasW + sway) % canvasW
                val rotation = (petal.initialRotation + animationTime * petal.rotationSpeed) % 360f

                rotate(degrees = rotation, pivot = Offset(x, y)) {
                    // Draw a sakura petal
                    val path = Path().apply {
                        moveTo(x, y - petal.size * 0.5f)
                        cubicTo(
                            x + petal.size * 0.6f, y - petal.size * 0.2f,
                            x + petal.size * 0.4f, y + petal.size * 0.6f,
                            x, y + petal.size * 0.8f
                        )
                        cubicTo(
                            x - petal.size * 0.4f, y + petal.size * 0.6f,
                            x - petal.size * 0.6f, y - petal.size * 0.2f,
                            x, y - petal.size * 0.5f
                        )
                    }
                    drawPath(
                        path = path,
                        color = petal.color.copy(alpha = 0.82f)
                    )
                }
            }
        }

        // App Logo (Red Circle with 日本বাংলা)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .alpha(alphaAnim.value)
                .scale(scaleAnim.value)
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(JapanRed, JapanRedDark)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "日本বাংলা",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "🇯🇵 🇧🇩",
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "জাপানি শিখি বাংলায়",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate900
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "N5 থেকে N1 — সহজে ও বাংলায়",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Slate500
            )
        }
    }
}
