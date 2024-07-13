package com.example.compose.ui

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.TAG
import com.example.compose.ui.theme.ComposeTheme

/**
 * Example of simple Compose chat UI
 *
 * Full tutorial:
 * https://developer.android.com/jetpack/compose/tutorial
 */

data class UserMessage(val message: String, val userName: String)

fun getDataForChat(): List<UserMessage> {
    return listOf(
        UserMessage("Hello this is my test very cool message, Hello this is my test very cool message",
            "Tom"),
        UserMessage("Hello this is my test very cool message, Hello this is my test very cool message",
            "Jenny"),
        UserMessage("Hello this is my test very cool message, Hello this is my test very cool message",
            "Sem"),
        UserMessage("Hello this is my test very cool message, Hello this is my test very cool message",
            "John"))
}

@Composable
fun Chat(userMessages: List<UserMessage>) {
    // This will apply material theme styling
    ComposeTheme {
        // This allows customization of shape and elevation
        Surface(modifier = Modifier.fillMaxSize()) {
            UserMessageList(userMessages)
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun ChatDark() {
    val userMessages = getDataForChat()
    // This will apply material theme styling
    ComposeTheme {
        // This allows customization of shape and elevation
        Surface(modifier = Modifier.fillMaxSize()) {
            UserMessageList(userMessages)
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun ChatLight() {
    val userMessages = getDataForChat()
    // This will apply material theme styling
    ComposeTheme {
        // This allows customization of shape and elevation
        Surface(modifier = Modifier.fillMaxSize()) {
            UserMessageList(userMessages)
        }
    }
}

@Composable
fun UserMessageList(message: List<UserMessage>) {
    // The horizontally scrollable list that only composes and lays out the currently visible items on the screen
    LazyColumn(modifier = Modifier.padding(all = 10.dp)) {
        // Display 2 items in a column
        items(message.size) {
            UserMessage(message[it])
        }
    }
}

@Composable
fun UserMessage(userMessage: UserMessage) {

    Log.i(TAG, "User(): recompose")

    // Container which places elements in a row
    Row(modifier = Modifier
        .padding(all = 10.dp)) {

        Image(painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "",
            modifier = Modifier
                // Set image size
                .size(40.dp)
                // Add a shape
                .clip(CircleShape)
                // Add a border
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape))

        Spacer(modifier = Modifier.width(10.dp))

        // A state which tracks a state of the column
        var isExpanded by remember { mutableStateOf(false) }
        // Or to survive during config changes
//        var isExpanded by rememberSaveable { mutableStateOf(false) }

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        // Container which places elements in a column
        Column(modifier =
        // Changing a state if button is clicked
        Modifier.clickable { isExpanded = !isExpanded }) {

            Text(text = userMessage.userName,
                // Set text size
                //fontSize = 20.sp,
                // Or inherit from style
                style = MaterialTheme.typography.bodyLarge,
                // Here we can use elements from our app theme
                color = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.width(8.dp))

            // Add background drawable image
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = userMessage.message,
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    // Set text size
                    //fontSize = 18.sp
                    // Or
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

}