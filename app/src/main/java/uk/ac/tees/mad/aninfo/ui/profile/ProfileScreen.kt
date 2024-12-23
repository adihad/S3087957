package uk.ac.tees.mad.aninfo.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.aninfo.PreferencesHelper
import uk.ac.tees.mad.aninfo.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val preferencesHelper = remember { PreferencesHelper(context) }
    var isBiometricAuthEnabled by remember { mutableStateOf(preferencesHelper.isBiometricAuthEnabled()) }

    val userProfile by viewModel.userProfile.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    Scaffold(
        containerColor = Color(0xFF31313D),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Profile")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF424242),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFF31313D)),
            contentAlignment = Alignment.Center
        ) {
            userProfile?.let { user ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF31313D))
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    // Profile Picture
                    Image(
                        painter = rememberAsyncImagePainter(
                            userProfile?.profilePictureUrl?.ifEmpty { "https://avatar.iran.liara.run/public" }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // User Information
                    Text(
                        text = user.name,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = user.email,
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color(0xFF3D5AFE), RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Biometric authentication", color = Color.White)

                        Switch(
                            checked = isBiometricAuthEnabled,
                            onCheckedChange = { checked ->
                                isBiometricAuthEnabled = checked
                                preferencesHelper.setBiometricAuthEnabled(checked)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Edit Profile Button
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color(0xFF3D5AFE), RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                navController.navigate(Screen.EditProfile.route)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Edit profile", color = Color.White)

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White
                        )

                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Watchlist screen
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color(0xFF3D5AFE), RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                navController.navigate(Screen.Watchlist.route)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Your watchlist", color = Color.White)

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White
                        )

                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Logout Button
                    Button(
                        onClick = {
                            viewModel.logout()
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        shape = RoundedCornerShape(16.dp)

                    ) {
                        Text(text = "Logout", color = Color.White)
                    }
                }
            } ?: run {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}