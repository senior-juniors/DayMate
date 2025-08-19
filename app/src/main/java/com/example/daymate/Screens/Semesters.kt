package com.example.daymate.Screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.daymate.Features.standardQuadFromTo

@Composable
fun StudyMaterial(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06154C)) // Base background color
    ) {
        // Wavy background drawing
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()

            val mediumColoredPath = Path().apply {
                val mediumColoredPoint1 = Offset(0f, height * 0.8f)
                val mediumColoredPoint2 = Offset(width * 0.25f, height * 0.9f)
                val mediumColoredPoint3 = Offset(width * 0.5f, height * 0.75f)
                val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.95f)
                val mediumColoredPoint5 = Offset(width * 1.4f, height * 0.6f)

                moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
                standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
                standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
                standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
                standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
                lineTo(width + 100f, height + 100f)
                lineTo(-100f, height + 100f)
                close()
            }

            val lightColoredPath = Path().apply {
                val lightPoint1 = Offset(0f, height * 0.85f)
                val lightPoint2 = Offset(width * 0.2f, height * 0.95f)
                val lightPoint3 = Offset(width * 0.5f, height * 0.85f)
                val lightPoint4 = Offset(width * 0.8f, height * 1.05f)
                val lightPoint5 = Offset(width * 1.4f, height * 0.7f)

                moveTo(lightPoint1.x, lightPoint1.y)
                standardQuadFromTo(lightPoint1, lightPoint2)
                standardQuadFromTo(lightPoint2, lightPoint3)
                standardQuadFromTo(lightPoint3, lightPoint4)
                standardQuadFromTo(lightPoint4, lightPoint5)
                lineTo(width + 100f, height + 100f)
                lineTo(-100f, height + 100f)
                close()
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawPath(path = mediumColoredPath, color = Color(0xFF494E8A))
                drawPath(path = lightColoredPath, color = Color(0xFFCACFFF))
            }
        }

        // Use LazyColumn for a scrollable list of cards
        LazyColumn(
            // This provides padding around the entire list of items.
            contentPadding = PaddingValues(16.dp),
            // This adds spacing between each item in the list.
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Loop 8 times to create a card for each semester.
            items(8) { index ->
                SemesterCard(
                    text = "${index + 1} Semester", // Dynamic text for each card
                    background = Color.White,
                    textColor = Color.Black,
                    mediumColor = Color(0xFF9FA4FF),
                    lightColor = Color(0xFFAEB4FE),
                    darkColor = Color(0xFF8F98FD),
                    onClick = {
                        // Handle click for the specific semester
                    }
                )
            }
        }
    }
}

@Composable
fun SemesterCard(
    modifier: Modifier = Modifier,
    text: String,
    background: Color = Color.White,
    textColor: Color = Color.Black,
    lightColor: Color,
    mediumColor: Color,
    darkColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Wavy Background Section for the card icon
        BoxWithConstraints(
            modifier = Modifier
                .padding(7.5.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(darkColor)
        ) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()

            // Medium colored path
            val mediumColoredPoint1 = Offset(0f, height * 0.3f)
            val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
            val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
            val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
            val mediumColoredPoint5 = Offset(width * 1.4f, -height)

            val mediumColoredPath = Path().apply {
                moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
                standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
                standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
                standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
                standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
                lineTo(width + 100f, height + 100f)
                lineTo(-100f, height + 100f)
                close()
            }

            // Light colored path
            val lightPoint1 = Offset(0f, height * 0.35f)
            val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
            val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
            val lightPoint4 = Offset(width * 0.65f, height)
            val lightPoint5 = Offset(width * 1.4f, -height / 3f)

            val lightColoredPath = Path().apply {
                moveTo(lightPoint1.x, lightPoint1.y)
                standardQuadFromTo(lightPoint1, lightPoint2)
                standardQuadFromTo(lightPoint2, lightPoint3)
                standardQuadFromTo(lightPoint3, lightPoint4)
                standardQuadFromTo(lightPoint4, lightPoint5)
                lineTo(width + 100f, height + 100f)
                lineTo(-100f, height + 100f)
                close()
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawPath(path = mediumColoredPath, color = mediumColor)
                drawPath(path = lightColoredPath, color = lightColor)
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Text
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 20.sp,
                fontSize = 20.sp
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun StudyMaterialScreenPreview() {
//    MaterialTheme {
//        val navcontroller = rememberNavController()
//        StudyMaterial(navController = navcontroller)
//    }
//}