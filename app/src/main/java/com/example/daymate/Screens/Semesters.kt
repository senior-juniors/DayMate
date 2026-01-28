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
import androidx.navigation.compose.rememberNavController
import com.example.daymate.Features.standardQuadFromTo
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Person
import com.example.daymate.Features.AnimatedDropdownSelector
import com.example.daymate.Features.PdfFile
import com.example.daymate.Features.PdfItemCard
import okhttp3.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

@Composable
fun StudyMaterialUnifiedScreen(navController: NavController) {
    val context = LocalContext.current



    val scriptUrl = "https://script.google.com/macros/s/AKfycbyqj8OvU3Eo7JkJWi9yj2VMSQdQ0QNutCxGqOjc4VudcnHQB6GB01fQmW37QNZtXxBI/exec" // Paste your Web App URL here

    var selectedSemester by remember { mutableStateOf<String?>(null) }
    var selectedBranch by remember { mutableStateOf<String?>(null) }
    var selectedExamType by remember { mutableStateOf<String?>(null) }

    var currentPdfList by remember { mutableStateOf<List<PdfFile>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    val semesters = (1..8).map { "$it Semester" }
    val branches = listOf("CSE", "ECE", "AIDE")
    val examTypes = listOf("Mid Sem", "End Sem")

    // NEW: State to hold the URL to display below the button
    var folderUrlToShow by remember { mutableStateOf<String?>(null) }



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

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text("Academic Materials", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(25.dp))

            AnimatedDropdownSelector("Select Semester", semesters, selectedSemester) { selectedSemester = it }
            AnimatedDropdownSelector("Select Branch", branches, selectedBranch) { selectedBranch = it }
            AnimatedDropdownSelector("Select Exam Type", examTypes, selectedExamType) { selectedExamType = it }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                // Inside the Button's onClick in StudyMaterialUnifiedScreen
                onClick = {
                    val semester = selectedSemester ?: ""
                    val branch = selectedBranch ?: ""
                    val exam = selectedExamType?.replace(" ", "") ?: ""
                    val key = "${branch}_$exam"

                    val folderUrl = DriveData.links[semester]?.get(key) ?: ""
                    val folderId = folderUrl.split("/").last()

                    if (folderId.isNotEmpty()) {
                        navController.navigate("pdf_list/$folderId")
                    }
                },
                enabled = selectedSemester != null && selectedBranch != null && selectedExamType != null && !isLoading,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8F98FD))
            ) {
                if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                else Text("Get Material", fontWeight = FontWeight.Bold)
            }

            if (currentPdfList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(30.dp))
                currentPdfList.forEach { pdf ->
                    PdfItemCard(pdf, context)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}


fun fetchPdfList(scriptUrl: String, folderId: String, onResult: (List<PdfFile>) -> Unit) {
    val client = OkHttpClient()

    // Constructing the URL with the Folder ID as a parameter
    val url = "$scriptUrl?id=$folderId"

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // Log the error so you can see it in Logcat
            e.printStackTrace()
            onResult(emptyList())
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.use { responseBody ->
                val json = responseBody.string()
                val gson = Gson()
                val itemType = object : TypeToken<List<PdfFile>>() {}.type

                try {
                    val list: List<PdfFile> = gson.fromJson(json, itemType)
                    // Ensure we return the result to the main thread for UI updates
                    onResult(list)
                } catch (e: Exception) {
                    e.printStackTrace()
                    onResult(emptyList())
                }
            }
        }
    })
}

// --- PREVIEW SECTION ---

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudyMaterialUnifiedPreview() {
    // We use a dummy NavController for the preview
    val navController = rememberNavController()

    MaterialTheme {
        StudyMaterialUnifiedScreen(navController = navController)
    }
}