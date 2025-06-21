package com.example.daymate.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.daymate.R

@Preview(showBackground = true, backgroundColor = 0xFF0A1A41)
@Composable
fun FirstPreview(){
    val navController= rememberNavController()
    DayMateFirstScreen(
        navController,
        onGoogleSignInClick = TODO()
    )
}

@Composable
fun DayMateFirstScreen(navController: NavController,
                      onGoogleSignInClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1A41)) // Dark Blue Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Top Logo
            Image(
                painter = painterResource(id = R.drawable.logo_img),
                contentDescription = "DayMate Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Welcome to DayMate",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Subtitle
            Text(
                text = "LET ACCESS ALL WORK FROM HERE",
                fontSize = 12.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp, start = (16).dp) // Top and Left positioning
            ) {
                Image(
                    painter = painterResource(id = R.drawable.firstpage_img),
                    contentDescription = "Illustration",
                    modifier = Modifier
                        .width(395.dp)
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Login Button
//            Button(
//                onClick = {
//                    navController.navigate("login")
//
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(48.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9BA9FF)),
//                shape = RoundedCornerShape(24.dp)
//            ) {
//                Text("Login", fontSize = 16.sp, color = Color.White)
//            }

//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Sign Up Button
//            Button(
//                onClick = {
//                    navController.navigate("signup")
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(48.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9BA9FF)),
//                shape = RoundedCornerShape(24.dp)
//            ) {
//                Text("Sign Up", fontSize = 16.sp, color = Color.White)
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))

            // OR Divider
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "  OR  ",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
            }

            // Social Icons Row
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onGoogleSignInClick()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_logo), // Add this in your drawable
                        contentDescription = "Google Login",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
    }
}
