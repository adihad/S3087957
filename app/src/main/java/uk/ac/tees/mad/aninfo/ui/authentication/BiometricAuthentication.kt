package uk.ac.tees.mad.aninfo.ui.authentication

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity

@Composable
fun BiometricAuthentication(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    TextButton(
        onClick = {
            Biometric.authenticate(
                context as FragmentActivity,
                title = "Biometric Authentication",
                subtitle = "Authenticate to proceed",
                description = "Authentication is must",
                negativeText = "Cancel",
                onSuccess = {
                    Toast.makeText(
                        context,
                        "Authenticated successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()

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
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Authenticate"
        )
    }
}