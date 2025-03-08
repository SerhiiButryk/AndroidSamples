package com.example.myapplication.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.Message
import com.example.myapplication.data.SampleData
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun Conversation(modifier: Modifier = Modifier, messages: List<Message>) {
    LazyColumn {
        items(messages) { mes ->
            Spacer(modifier = Modifier.height(4.dp))
            MessageCard(message = mes)
        }
    }
}

@Preview
@Composable
private fun PreviewConversation() {
    MyApplicationTheme {
        Surface {
            Conversation(messages = SampleData.conversationSample)
        }
    }
}

@Composable
fun MessageCard(modifier: Modifier = Modifier, message: Message) {

    Log.i("MainActivity", "MessageCard: add new MessageCard")

    Row {

        Log.i("MainActivity", "MessageCard: add new row")

        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.8.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor by animateColorAsState(
            targetValue = if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = ""
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {

            Text(text = message.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall)

            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp, color = surfaceColor) {
                Text(text = message.body,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(all = 4.dp),
                    // Expand if necessary
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1)
            }
        }
    }
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMessageCard(modifier: Modifier = Modifier) {
    MyApplicationTheme {
        Surface {
            MessageCard(message = Message("Author", "Some body"))
        }
    }
}