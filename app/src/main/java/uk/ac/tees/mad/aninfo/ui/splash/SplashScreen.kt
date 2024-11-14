package uk.ac.tees.mad.aninfo.ui.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.ac.tees.mad.aninfo.R
import uk.ac.tees.mad.aninfo.navigation.Screen

@Composable
fun SplashScreen(navController: NavHostController) {
    // Define a state for the animation
    val scale = remember { Animatable(0f) }

    // Start the animation when the screen is first composed
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            })
        )
        delay(3000) // Hold the splash screen for 2 seconds
        // Navigate based on authentication status (placeholder for real check)
        launch(Dispatchers.Main) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    // UI of the Splash Screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF31313D)), // Your primary color
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.aninfo_logo), // Your app logo resource
                contentDescription = "App Logo",
                modifier = Modifier
                    .scale(scale.value)
                    .clip(CircleShape)
                    .size(150.dp) // Adjust size as needed
            )

            Spacer(modifier = Modifier.height(16.dp))

            // App Name
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}
