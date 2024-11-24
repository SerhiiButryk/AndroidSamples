package com.example.myapplication.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.network.NetworkTaskManager
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MainUI(networkTaskManager: NetworkTaskManager) {
    MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {

                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {
                        networkTaskManager.startRequest()
                    },
                ) {
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {
                        networkTaskManager.cancelRequest()
                    },
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainUI(NetworkTaskManager())
}

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GreetingPreviewNight() {
    MainUI(NetworkTaskManager())
}