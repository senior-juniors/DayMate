package com.example.daymate.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.daymate.R
import com.example.daymate.auth.UserViewmodel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text


fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        (from.x + to.x) / 2f,
        (from.y + to.y) / 2f
    )
}

@Composable
fun ProfileScreen(navController: NavController) {

    val context = LocalContext.current
    val userViewModel: UserViewmodel = viewModel()

    Box(modifier = Modifier.fillMaxSize()
        .background(Color(0xFF06154C))
    ) {
        val user = userViewModel.user_Data

        // Fetch data once when the screen loads
        LaunchedEffect(Unit) {
            userViewModel.fetchUserData()
        }

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
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(48.dp))

//                if (user != null) {
//                    Text(text = "Name: ${user.name}", style = MaterialTheme.typography.body1)
//                    Text(text = "Email: ${user.email}", style = MaterialTheme.typography.body1)
//                    Text(
//                        text = "Semester: ${user.semester}",
//                        style = MaterialTheme.typography.body1
//                    )
//                    Text(text = "Branch: ${user.branch}", style = MaterialTheme.typography.body1)
//                    Text(text = "Batch: ${user.batch}", style = MaterialTheme.typography.body1)
//                    Text(text = "Club: ${user.club}", style = MaterialTheme.typography.body1)
//                } else {
//                    CircularProgressIndicator()
//                }

            if (user != null) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // User Name & Email
                androidx.compose.material3.Text(
                    text = "${user.name}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))

                androidx.compose.material3.Text(
                    text = "${user.email}",
                    fontSize = 16.sp,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Details Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.1f)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        ProfileInfoRow(
                            icon = Icons.Default.School,
                            label = "Branch",
                            value = "${user.branch}"
                        )
                        ProfileInfoRow(
                            icon = Icons.Default.Bookmark,
                            label = "Semester",
                            value = "${user.semester}"
                        )
                        ProfileInfoRow(
                            icon = Icons.Default.Groups,
                            label = "Club",
                            value = "${user.club}"
                        )
                        ProfileInfoRow(
                            icon = Icons.Default.CalendarToday,
                            label = "Batch",
                            value = "${user.batch}"
                        )
                    }
                }
            }
            Button(
                onClick = {
                    userViewModel.signOut()

                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    googleSignInClient.signOut().addOnCompleteListener {
                        // Navigate to the FirstScreen (Google login screen)
                        navController.navigate("FirstScreen") {
                            popUpTo(0) { inclusive = true } // Clear entire backstack
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier.padding(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8CA8DA),
                    contentColor = Color.White,
                )
            ){
                Text("Sign Out")
            }
        }
    }
}



@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label icon",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            androidx.compose.material3.Text(
                text = label,
                color = Color.LightGray,
                fontSize = 12.sp
            )
            androidx.compose.material3.Text(
                text = value,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}





