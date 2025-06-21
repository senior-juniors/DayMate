package com.example.daymate.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daymate.R


@Composable
fun ClubConfirmScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Clubconfirm()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_img), // Replace with your actual logo resource
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Question
            Text(
                text = "Are you part of any Clubs ?",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Yes / No Buttons
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ClubConfirmButton(text = "Yes", onClick = { navController.navigate("clubscreen") })
                Spacer(modifier = Modifier.height(8.dp))
                ClubConfirmButton(text = "No", onClick = { navController.navigate("dashboard") })
            }
        }
    }
}

@Composable
fun ClubConfirmButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFB1B2FF))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun Clubconfirm(
    lightColor: Color = Color(0xFFCACFFF),
    mediumColor: Color = Color(0xFF494E8A),
    darkColor: Color = Color(0xFF06154C),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(darkColor)
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            with(this) {
                val width = constraints.maxWidth.toFloat()
                val height = constraints.maxHeight.toFloat()

                val mediumPath = Path().apply {
                    moveTo(0f, height * 0.65f)
                    quadraticBezierTo(width * 0.25f, height * 0.7f, width * 0.5f, height * 0.65f)
                    quadraticBezierTo(width * 0.75f, height * 0.6f, width * 1.1f, height * 0.8f)
                    lineTo(width + 100f, height + 100f)
                    lineTo(-100f, height + 100f)
                    close()
                }

                val lightPath = Path().apply {
                    moveTo(0f, height * 0.8f)
                    quadraticBezierTo(width * 0.3f, height * 0.85f, width * 0.6f, height * 0.75f)
                    quadraticBezierTo(width * 0.9f, height * 0.65f, width * 1.2f, height * 0.9f)
                    lineTo(width + 100f, height + 100f)
                    lineTo(-100f, height + 100f)
                    close()
                }

                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawPath(path = mediumPath, color = mediumColor)
                    drawPath(path = lightPath, color = lightColor)
                }
            }
        }
    }
}