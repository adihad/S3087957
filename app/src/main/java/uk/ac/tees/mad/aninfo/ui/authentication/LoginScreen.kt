package uk.ac.tees.mad.aninfo.ui.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import uk.ac.tees.mad.aninfo.PreferencesHelper
import uk.ac.tees.mad.aninfo.R
import uk.ac.tees.mad.aninfo.navigation.Screen

@Composable
fun LoginScreen(navController: NavHostController, viewModel: AuthViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()
    val preferencesHelper = remember { PreferencesHelper(context) }
    var isBiometricAuthEnabled by remember { mutableStateOf(preferencesHelper.isBiometricAuthEnabled()) }


    LaunchedEffect(Unit) {
        if (Firebase.auth.currentUser != null) {
            if (isBiometricAuthEnabled) {
//            if (!Biometric.status(context)) {
//                Toast.makeText(
//                    context,
//                    Biometric.statusName(context),
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@LaunchedEffect
//            }

                Biometric.authenticate(
                    context as FragmentActivity,
                    title = "Biometric Authentication",
                    subtitle = "Authenticate to proceed",
                    description = "Authentication is must",
                    negativeText = "Cancel",
                    onSuccess = {
                        navController.navigate(Screen.Home.route)
                    },
                    onError = { errorCode, errorString ->

                        Toast.makeText(
                            context,
                            "Authentication error: $errorCode, $errorString",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    },
                    onFailed = {
                        Toast.makeText(
                            context,
                            "Authentication failed",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    }
                )
            } else {
                navController.navigate(Screen.Home.route)
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFF31313D)
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFF31313D)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.aninfo_logo), // Your app logo resource
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(150.dp) // Adjust size as needed
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Title
                Text(
                    text = "Welcome back to Ani-Info",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Enter your credentials to see anime info.",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Email Text Field
                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.White) },
                    placeholder = { Text("Enter your email", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Text Field
                var password by remember { mutableStateOf("") }
                var passwordVisible by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color.White) },
                    placeholder = { Text("Enter your password", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Default.Visibility
                        else Icons.Default.VisibilityOff

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = image,
                                contentDescription = "Toggle Password Visibility",
                                tint = Color.White
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                Button(
                    onClick = {
                        viewModel.login(email = email, password = password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    if (authState.isLoading) {
                        CircularProgressIndicator(
                            color = Color(0xFF31313D),
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("Login", color = Color(0xFF31313D), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Supporting Text
                Text(
                    text = "Forgot your password?",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable {
                        // Handle forgot password
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Don't have an account? Sign Up",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Register.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                authState.error?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            if (authState.isAuthenticated) {
                // Navigate to the Home Screen
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }
        }
    }
}
