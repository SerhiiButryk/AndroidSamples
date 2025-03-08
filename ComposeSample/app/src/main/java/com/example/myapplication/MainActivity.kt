package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.SampleData
import com.example.myapplication.ui.Conversation
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Conversation(messages = SampleData.conversationSample)
                }
            }
        }
    }

    val list = listOf("John", "David")

    @Preview(showBackground = true, widthDp = 320, showSystemUi = true)
    @Composable
    private fun PreviewTestCompose(names: List<String> = list) {
        MyApplicationTheme {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                for (name in names) {
                    TextItem()
                }
            }
        }
    }

    @Composable
    fun TextItem(modifier: Modifier = Modifier) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
            ) {
            Row(modifier = Modifier.padding(8.dp)) {
                // Allows to place elements relative to each other
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Hello")
                    Text(text = "John")
                }
                ElevatedButton(onClick = { /*TODO*/ }) {
                    Text(text = "Show more")
                }
            }
        }
    }

}