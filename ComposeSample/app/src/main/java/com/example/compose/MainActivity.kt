package com.example.compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme

/**
 * Example of simple Compose UI interface
 *
 * Full tutorial:
 * https://developer.android.com/jetpack/compose/tutorial
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This will set ComposeView as root view
        setContent {
            // This will apply material theme styling
            ComposeTheme {
                // This allows customization of shape and elevation
                Surface(modifier = Modifier.fillMaxSize()) {
                    UserList()
                }
            }
        }
    }
}

// Defines a User UI view for our screen

// More:
// https://developer.android.com/jetpack/compose/tutorial?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-1%23article-https%3A%2F%2Fdeveloper.android.com%2Fjetpack%2Fcompose%2Ftutorial

// Enable preview for Dark and light mode
@Preview(name = "Dark Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun User() {

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

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        // Container which places elements in a column
        Column(modifier =
            // Changing a state if button is clicked
            Modifier.clickable { isExpanded = !isExpanded }) {

            Text(text = "My first name",
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
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = "My last name",
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    // Set text size
                    //fontSize = 18.sp
                    // Or
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// Defines a list of User UI view for our screen
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun UserList() {
    val numberOfUsers = 2
    // The horizontally scrolling list that only composes and lays out the currently visible items
    LazyColumn(modifier = Modifier.padding(all = 10.dp)) {
        // Display 2 items in a column
        items(numberOfUsers) {
            User()
        }
    }
}