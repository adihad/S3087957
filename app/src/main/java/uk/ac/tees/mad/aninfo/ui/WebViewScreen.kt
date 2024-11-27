package uk.ac.tees.mad.aninfo.ui

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(navController: NavHostController, urlString: String) {
    val titleString = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = titleString.value,
                        style = MaterialTheme.typography.titleSmall
                    )
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
        },
        containerColor = Color(0xFF31313D)
    ) { padding ->
        var isLoading by remember { mutableStateOf(true) }
        var progress by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (isLoading) {
                LinearProgressIndicator(
                    progress = { progress / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    color = Color.Black,
                )
            }
            AndroidView(
                factory = {
                    WebView(it).apply {
                        webViewClient = WebViewClient()
                        webChromeClient = object : WebChromeClient() {
                            override fun onReceivedTitle(view: WebView?, title: String?) {
                                super.onReceivedTitle(view, title)
                                titleString.value = title ?: ""
                            }

                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                progress = newProgress
                                isLoading = newProgress < 100
                                super.onProgressChanged(view, newProgress)
                            }
                        }
                        settings.javaScriptEnabled = true
                        loadUrl(urlString)
                    }
                },
                update = { webView ->
                    webView.loadUrl(urlString)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}