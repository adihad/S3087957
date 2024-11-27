package uk.ac.tees.mad.aninfo.ui.authentication


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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uk.ac.tees.mad.aninfo.R
import uk.ac.tees.mad.aninfo.navigation.Screen

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: AuthViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

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
                    text = "Welcome to Ani-Info",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Enter your details to see explore anime info.",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Name Text Field
                var name by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name", color = Color.White) },
                    placeholder = { Text("Enter your full name", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

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
                        unfocusedTextColor = Color.White
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
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
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
                        viewModel.register(name, email, password)
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
                        Text("Register", color = Color(0xFF31313D), fontWeight = FontWeight.Bold)
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
                    text = "Already have an account? Sign in",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
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
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            }

        }
    }
}
