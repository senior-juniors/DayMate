package com.example.daymate

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.daymate.Class_Schedule.TimetableScreen
import com.example.daymate.Navigation.ScreenNavigation
import com.example.daymate.todo.viewmodel.ToDoViewModel
import com.example.daymate.ui.theme.DayMateTheme
import org.example.project.FloatButton

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        val todoViewmodel = ViewModelProvider(this)[ToDoViewModel::class.java]
        setContent {
            DayMateTheme {
                //ScreenNavigation()
                //Animation()
                // FloatButton()
                //TimetableScreen()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    FloatButton(todoViewmodel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Animation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animate))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )

}