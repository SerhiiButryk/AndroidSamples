package com.example.compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.compose.ui.Chat
import com.example.compose.ui.UserMessage
import com.example.compose.ui.getDataForChat

const val TAG: String = "MainActivity"

/**
 * Android tutorial:
 * https://developer.android.com/courses/jetpack-compose/course
 */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This will set ComposeView as root view
        setContent {
            //Chat(userMessages = getDataForChat())
            SimpleUI()
        }
    }

    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        showBackground = true,
        name = "Light Mode"
    )
    @Composable
    fun SimpleUI() {
        Row(modifier = Modifier
            .background(Color.Blue)
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center) {
          Text("Hello", fontSize = 20.sp)
        }

    }

}