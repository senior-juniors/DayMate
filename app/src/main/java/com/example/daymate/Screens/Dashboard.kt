package com.example.daymate.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daymate.Features.Feature
import com.example.daymate.Features.standardQuadFromTo
import com.example.daymate.R
import com.example.daymate.auth.UserViewmodel
import android.icu.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController,userViewModel: UserViewmodel) {
    var showSheet by remember { mutableStateOf(false) }
    var timetableList by remember { mutableStateOf<List<String>>(emptyList()) }


    LaunchedEffect(Unit) {
        // Only fetch if userData is not already present.
        if (userViewModel.user_Data == null) {
            userViewModel.fetchUserData()
        }
    }
    val userData = userViewModel.user_Data
    val name=userData?.name ?:""
    val semester = userData?.semester ?: ""
    val branch = userData?.branch ?: ""
    val batch = userData?.batch ?: ""

    LaunchedEffect(semester, branch, batch) {
        if (semester.isNotEmpty() && branch.isNotEmpty() && batch.isNotEmpty()) {
            userViewModel.fetchTodaysTimetable(
                semester = semester,
                branch = branch,
                batch = batch
            ) { fetchedList ->
                timetableList = fetchedList
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06154C)) // Optional base background
    ) {
        // Replacing old Canvas with new background
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            with(this) {
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

            // Dashboard Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Greeting
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val greeting = remember {
                        val calendar = Calendar.getInstance()
                        when (calendar.get(Calendar.HOUR_OF_DAY)) {
                            in 0..11 -> "Good Morning!"
                            in 12..16 -> "Good Afternoon!"
                            else -> "Good Evening!"
                        }
                    }

                    Column {
                        Text(
                            text = greeting,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )
                        Text(text = name, color = Color.White)
                    }
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
                        modifier = Modifier
                            .size(40.dp)
                            .shadow(
                                elevation = 8.dp,       // Adjust this for deeper/lighter shadow
                                shape = CircleShape,    // Ensures shadow follows the circular shape
                                clip = false            // Set to false to let shadow be drawn outside bounds
                            )
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable{navController.navigate("profileScreen")},
                        tint = Color(0xFF0F1D57)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Search Bar
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Search Your Classes") },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            Image(
                                painter = painterResource(R.drawable.menu_book_24),
                                contentDescription = "Notebook Icon",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    )
//                    Spacer(modifier = Modifier.width(8.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))


                if (showSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {showSheet=false},
                        modifier = Modifier.fillMaxHeight(0.6f),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        Text(
                            "Today's Timetable",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp)
                        )
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        if (timetableList.isEmpty()) {
                            Text(
                                "No classes today!",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(16.dp)
                            )

                        } else {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                timetableList.forEach { classEntry ->
                                    Text(
                                        text = classEntry,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                    DashboardCard(
                        icon = R.drawable.enjoy_img,
                        text = "Your\nToday's Classes",
                        background = Color.White,
                        textColor = Color.Black,
                        mediumColor = Color(0xFF11D79A),
                        lightColor = Color(0xFF54E1B6),
                        darkColor = Color(0xFF28BD90),
                        onClick = {showSheet=true}
                    )

                Spacer(modifier = Modifier.height(12.dp))

                DashboardCard(
                    icon = R.drawable.group_study,
                    text = "Pending\nAssignments",
                    background = Color.White,
                    textColor = Color.Black,
                    mediumColor = Color(0xFF9FA4FF),
                    lightColor = Color(0xFFAEB4FE),
                    darkColor = Color(0xFF8F98FD),
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Grid Section
                DashboardGridSection()
            }
        }
    }
}


@Composable
fun DashboardGridSection() {
    val gridItems = listOf(
        Feature(
            "Events",
            R.drawable.enjoy_img,
            Color(0xFFD0D1FF),
            Color(0xFFB1B2FF),
            Color(0xFF9293F0)
        ),
        Feature(
            "Clubs Meeting",
            R.drawable.group_study,
            Color(0xFFFFEDC2),
            Color(0xFFFFDD95),
            Color(0xFFE6C76F)
        ),
        Feature(
            "Study Material",
            R.drawable.enjoy_img,
            Color(0xFFAEE5D4),
            Color(0xFF98D7C2),
            Color(0xFF77C6A8)
        ),
        Feature(
            "Canteen",
            R.drawable.group_study,
            Color(0xFFFFD1D3),
            Color(0xFFFFB6B9),
            Color(0xFFE59497)
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
        items(gridItems.size) { index ->
            FeatureItem(feature = gridItems[index])
        }
    }
}


//@Composable
//fun DashboardCard(
//    icon: Int,
//    text: String,
//    background: Color = Color.White,
//    textColor: Color = Color.Black,
//    lightColor: Color,
//    mediumColor: Color,
//    darkColor: Color,
//    onClick: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .clickable { onClick() }
//            .fillMaxWidth()
//            .height(100.dp)
//            .clip(RoundedCornerShape(16.dp))
//            .background(background)
//            .padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//
//    ) {
//        // Wavy Background Section
//        BoxWithConstraints(
//            modifier = Modifier
//                .padding(7.5.dp)
//                .aspectRatio(1f)
//                .clip(RoundedCornerShape(10.dp))
//                .background(darkColor)
//        ) {
//            with(this) {
//                val width = constraints.maxWidth.toFloat()
//                val height = constraints.maxHeight.toFloat()
//
//                // Medium colored path
//                val mediumColoredPoint1 = Offset(0f, height * 0.3f)
//                val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
//                val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
//                val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
//                val mediumColoredPoint5 = Offset(width * 1.4f, -height)
//
//                val mediumColoredPath = Path().apply {
//                    moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
//                    standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
//                    standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
//                    standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
//                    standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
//                    lineTo(width + 100f, height + 100f)
//                    lineTo(-100f, height + 100f)
//                    close()
//                }
//
//                // Light colored path
//                val lightPoint1 = Offset(0f, height * 0.35f)
//                val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
//                val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
//                val lightPoint4 = Offset(width * 0.65f, height)
//                val lightPoint5 = Offset(width * 1.4f, -height / 3f)
//
//                val lightColoredPath = Path().apply {
//                    moveTo(lightPoint1.x, lightPoint1.y)
//                    standardQuadFromTo(lightPoint1, lightPoint2)
//                    standardQuadFromTo(lightPoint2, lightPoint3)
//                    standardQuadFromTo(lightPoint3, lightPoint4)
//                    standardQuadFromTo(lightPoint4, lightPoint5)
//                    lineTo(width + 100f, height + 100f)
//                    lineTo(-100f, height + 100f)
//                    close()
//                }
//
//                Canvas(modifier = Modifier.fillMaxSize()) {
//                    drawPath(path = mediumColoredPath, color = mediumColor)
//                    drawPath(path = lightColoredPath, color = lightColor)
//                }
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(15.dp)
//                )
//            }
//        }
//
//        // Image
//        Image(
//            painter = painterResource(id = icon),
//            contentDescription = null,
//            modifier = Modifier
//                .size(84.dp)
//        )
//
//        Spacer(modifier = Modifier.width(12.dp))
//
//        // Text
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .padding(end = 8.dp),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = text,
//                color = textColor,
//                style = MaterialTheme.typography.bodyLarge,
//                lineHeight = 20.sp
//            )
//        }
//    }
//}



@Composable
fun DashboardCard(
    icon: Int,
    text: String,
    background: Color = Color.White,
    textColor: Color = Color.Black,
    lightColor: Color,
    mediumColor: Color,
    darkColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Box(
            modifier = Modifier
                .size(84.dp), // Set a fixed size for the icon container
            contentAlignment = Alignment.Center
        ) {
            // Wavy Background Section
            BoxWithConstraints(
                modifier = Modifier
                    .padding(7.5.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(darkColor)
            ) {
                with(this) {
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
            }

            // Image
            Image(
                painter = painterResource(id = icon),
                contentDescription = text,
                modifier = Modifier
                    .size(50.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Text
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}





@Composable
fun FeatureItem(
    feature: Feature
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .shadow( // Added shadow for elevation
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ) {
        with(this) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()

            // Medium colored path
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

            // Light colored path
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
                drawPath(path = mediumColoredPath, color = feature.mediumColor)
                drawPath(path = lightColoredPath, color = feature.lightColor)
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = feature.iconId),
                        contentDescription = feature.title,
                        modifier = Modifier.size(94.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = feature.title,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


