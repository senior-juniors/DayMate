package com.example.daymate.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.daymate.Features.standardQuadFromTo
import com.example.daymate.R
import com.example.daymate.auth.UserViewmodel


@Composable
fun SemesterSelectionScreen(navController: NavController, userViewmodel: UserViewmodel) {


    var selectedSemester by remember { mutableStateOf<String?>(null) }
    var selectedBranch by remember { mutableStateOf<String?>(null) }
    var selectedBatch by remember { mutableStateOf<String?>(null) }

    val semesters = (1..8).map { "Semester $it" }
    val branches = listOf("CSE", "ECE", "AIDE")
    val batches = listOf("A1", "A2", "A3", "A4", "A5")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06154C)) // Optional base background
    ) {

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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Logo Image
                Image(
                    painter = painterResource(id = R.drawable.logo_img),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 24.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    AnimatedDropdownSelector(
                        label = "Select Semester",
                        options = semesters,
                        selectedOption = selectedSemester,
                        onOptionSelected = { selectedSemester = it }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedDropdownSelector(
                        label = "Select Branch",
                        options = branches,
                        selectedOption = selectedBranch,
                        onOptionSelected = { selectedBranch = it }
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    AnimatedDropdownSelector(
                        label = "Select Batch",
                        options = batches,
                        selectedOption = selectedBatch,
                        onOptionSelected = { selectedBatch = it }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            selectedSemester?.let { semester ->
                                selectedBranch?.let { branch ->
                                    selectedBatch?.let { batch ->
                                        userViewmodel.updateAcademicInfo(semester,branch,batch)
                                        userViewmodel.updateUserDetails{ }
                                            navController.navigate("clubscreen")
                                    }
                                }
                            }
                        },
                        enabled = selectedSemester != null && selectedBranch != null && selectedBatch != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0F1D57),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Continue",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedDropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize() // Animate height changes when dropdown expands
            .padding(bottom = 12.dp)
    ) {
        // Dropdown Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF8F98FD))
                .clickable { expanded = !expanded }
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(26.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = selectedOption ?: label,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        // Animated dropdown content
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            ) {
                options.forEach { option ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (selectedOption == option) Color(0xFF3A7DFF).copy(alpha = 0.1f)
                                else Color.White.copy(alpha = 0.9f)
                            )
                            .border(
                                1.dp,
                                if (selectedOption == option) Color(0xFF3A7DFF)
                                else Color.LightGray.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                onOptionSelected(option)
                                expanded = false
                            }
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = option,
                            color = if (selectedOption == option) Color(0xFF2A4D8F) else Color(
                                0xFF555555
                            ),
                            fontSize = 16.sp,
                            fontWeight = if (selectedOption == option) FontWeight.Medium else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SemesterItem(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Color(0xFF3A7DFF).copy(alpha = 0.1f)
    } else {
        Color.White.copy(alpha = 0.9f)
    }

    val borderColor = if (isSelected) {
        Color(0xFF3A7DFF)
    } else {
        Color.LightGray.copy(alpha = 0.5f)
    }

    val textColor = if (isSelected) {
        Color(0xFF2A4D8F)
    } else {
        Color(0xFF555555)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onSelect() }
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}


