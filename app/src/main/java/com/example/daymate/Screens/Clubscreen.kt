package com.example.daymate.Screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daymate.Features.ClubItem
import com.example.daymate.Features.standardQuadFromTo
import com.example.daymate.R
import com.example.daymate.auth.UserViewmodel


@Composable
fun ClubScreen(navController: NavHostController, userViewmodel: UserViewmodel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1D57)) // Deep Blue Background
    ) {
        // Wavy Background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                moveTo(0f, height * 0.7f)
                quadraticBezierTo(width * 0.25f, height * 0.85f, width * 0.5f, height * 0.8f)
                quadraticBezierTo(width * 0.75f, height * 0.75f, width, height * 0.9f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }

            drawPath(path = path, color = Color(0xFFD6E1FF)) // Light Lavender
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(24.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_img),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Select Clubs You Are Part Of",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Club Grid
            ClubCardGridSection(navController, userViewmodel)
        }
    }
}

@Composable
fun ClubCardGridSection(navController: NavHostController, userViewmodel: UserViewmodel) {
    val gridItems = listOf(
        ClubItem(
            "Codebase",
            R.drawable.logo_img,
            Color(0xFFB1B2FF),
            Color(0xFFC1C2FF),
            Color(0xFFDADCFF)
        ),
        ClubItem(
            "IIIT Kernel",
            R.drawable.kernel,
            Color(0xFFFFDD95),
            Color(0xFFFFE4A8),
            Color(0xFFFFEDC2)
        ),
        ClubItem(
            "Algorithmus",
            R.drawable.algorithmus,
            Color(0xFFDD82FF),
            Color(0xFFE7A6FF),
            Color(0xFFF3CCFF)
        ),
        ClubItem(
            "ARC Robotics",
            R.drawable.kernel,
            Color(0xFFFF715B),
            Color(0xFFFF8A76),
            Color(0xFFFFB3A8)
        ),
        ClubItem(
            "Google Developer Group",
            R.drawable.cypher,
            Color(0xFFB1B2FF),
            Color(0xFFC1C2FF),
            Color(0xFFDADCFF)
        ),
        ClubItem(
            "Cypher",
            R.drawable.cypher,
            Color(0xFFB1B2FF),
            Color(0xFFC1C2FF),
            Color(0xFFDADCFF)
        ),
        ClubItem("Neon", R.drawable.inco, Color(0xFFFFDD95), Color(0xFFFFE4A8), Color(0xFFFFEDC2)),
        ClubItem(
            "Fit India",
            R.drawable.fitindia,
            Color(0xFFDD82FF),
            Color(0xFFE7A6FF),
            Color(0xFFF3CCFF)
        ),
        ClubItem(
            "Artive",
            R.drawable.fitindia,
            Color(0xFFFF715B),
            Color(0xFFFF8A76),
            Color(0xFFFFB3A8)
        ),
        ClubItem(
            "Oddesey",
            R.drawable.oddesey,
            Color(0xFFFF715B),
            Color(0xFFFF8A76),
            Color(0xFFFFB3A8)
        ),
        ClubItem(
            "QNS",
            R.drawable.logo_img,
            Color(0xFFFF715B),
            Color(0xFFFF8A76),
            Color(0xFFFFB3A8)
        ),
        ClubItem(
            "Incognito",
            R.drawable.incognito,
            Color(0xFFFF715B),
            Color(0xFFFF8A76),
            Color(0xFFFFB3A8)
        ),
        ClubItem(
            "Team Green",
            R.drawable.kernel,
            Color(0xFFFF715B),
            Color(0xFFFF8A76),
            Color(0xFFFFB3A8)
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(gridItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        userViewmodel.updateClub(item.title)
                        navController.navigate("dashboard")
                    }
                    .aspectRatio(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        CurvedBackground(
                            darkColor = item.darkColor,
                            mediumColor = item.mediumColor,
                            lightColor = item.lightColor,
                            modifier = Modifier.fillMaxSize()
                        )

                        Image(
                            painter = painterResource(id = item.imageRes),
                            contentDescription = item.title,
                            modifier = Modifier
                                .size(60.dp)
                                .align(Alignment.Center),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        maxLines = 1
                    )
                }
            }
        }
    }
}


@Composable
fun CurvedBackground(
    modifier: Modifier = Modifier,
    darkColor: Color,
    mediumColor: Color,
    lightColor: Color
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(darkColor)
    ) {
        with(this) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()

            val mediumColoredPath = Path().apply {
                moveTo(0f, height * 0.3f)
                standardQuadFromTo(Offset(0f, height * 0.3f), Offset(width * 0.1f, height * 0.35f))
                standardQuadFromTo(
                    Offset(width * 0.1f, height * 0.35f),
                    Offset(width * 0.4f, height * 0.05f)
                )
                standardQuadFromTo(
                    Offset(width * 0.4f, height * 0.05f),
                    Offset(width * 0.75f, height * 0.7f)
                )
                standardQuadFromTo(
                    Offset(width * 0.75f, height * 0.7f),
                    Offset(width * 1.4f, -height)
                )
                lineTo(width + 100f, height + 100f)
                lineTo(-100f, height + 100f)
                close()
            }

            val lightColoredPath = Path().apply {
                moveTo(0f, height * 0.35f)
                standardQuadFromTo(Offset(0f, height * 0.35f), Offset(width * 0.1f, height * 0.4f))
                standardQuadFromTo(
                    Offset(width * 0.1f, height * 0.4f),
                    Offset(width * 0.3f, height * 0.35f)
                )
                standardQuadFromTo(
                    Offset(width * 0.3f, height * 0.35f),
                    Offset(width * 0.65f, height)
                )
                standardQuadFromTo(
                    Offset(width * 0.65f, height),
                    Offset(width * 1.4f, -height / 3f)
                )
                lineTo(width + 100f, height + 100f)
                lineTo(-100f, height + 100f)
                close()
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawPath(path = mediumColoredPath, color = mediumColor)
                drawPath(path = lightColoredPath, color = lightColor)
            }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ClubScreenPreview() {
//    ClubScreen(navController)
//}

