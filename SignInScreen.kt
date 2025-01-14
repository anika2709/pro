package com.example.final_project_mobile_app.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.final_project_mobile_app.MainActivity
import kotlin.reflect.KFunction0

@Composable
fun SignInScreen(navController: NavHostController, launchGoogleSignIn: KFunction0<Unit>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome back",
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { (navController.context as MainActivity).launchGoogleSignIn() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
        ) {
            Text("Sign In with Google", color = Color.White)
        }
    }
}
