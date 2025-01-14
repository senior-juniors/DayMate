package com.example.daymate

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.daymate.Navigation.ScreenNavigation
import com.example.daymate.todo.viewmodel.ToDoViewModel

import org.example.project.FloatButton

import androidx.activity.enableEdgeToEdge
import com.example.compose.DayMateTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val todoViewmodel = ViewModelProvider(this)[ToDoViewModel::class.java]
//        enableEdgeToEdge()
        setContent {
            DayMateTheme {


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    FloatButton(todoViewmodel)
                }
//               ScreenNavigation()
//                Animation()

            }
        }
    }
}

//@Composable
//fun Animation() {
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animate))
//
//    val progress by animateLottieCompositionAsState(
//        composition=composition,
//        iterations = LottieConstants.IterateForever
//    )
//    LottieAnimation(
//        composition = composition,
//        progress=progress,
//        modifier = Modifier.fillMaxWidth()
//            .height(300.dp)
//    )
//
//}

