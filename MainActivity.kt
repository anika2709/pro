package com.example.final_project_mobile_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.final_project_mobile_app.ui.theme.SignInScreen
import com.example.final_project_mobile_app.ui.theme.ProfileSetupScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private var navController: NavHostController? = null // NavController is stored as a variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Setup GoogleSignInOptions
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Make sure this is set in strings.xml
            .requestEmail()
            .build()

        setContent {
            navController = rememberNavController() // Assign NavController
            NavHost(navController = navController!!, startDestination = "signIn") {
                composable("signIn") { SignInScreen(navController!!, ::launchGoogleSignIn) }
                composable("profileSetup") { ProfileSetupScreen() }
            }
        }
    }

    private fun ProfileSetupScreen() {
        TODO("Not yet implemented")
    }

    // Activity result handler for Google Sign-In
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        // Navigate to profileSetup using NavController
                        navController?.navigate("profileSetup")
                    } else {
                        Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    fun launchGoogleSignIn() {
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
}
