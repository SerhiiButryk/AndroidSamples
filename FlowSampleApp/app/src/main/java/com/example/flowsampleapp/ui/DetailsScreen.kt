package com.example.flowsampleapp.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flowsampleapp.ui.theme.MainAppTheme

@Composable
fun DetailsScreen(modifier: Modifier = Modifier, navigateToMainScreen: () -> Unit = {}) {
    MainAppTheme {
        DetailsScreenUI(modifier, navigateToMainScreen)
    }
}

@Preview("Default", showSystemUi = true)
@Preview("Default", showSystemUi = true, device = "spec:parent=pixel_5,orientation=landscape")
@Preview("Dark mode", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailsScreenUIPreview() {
    MainAppTheme {
        DetailsScreenUI()
    }
}

@Composable
private fun DetailsScreenUI(
    modifier: Modifier = Modifier,
    navigateToMainScreen: () -> Unit = {}
) {

    Log.i("COMPOSE", "DetailsScreenUI() IN")

    StartUI(
        modifier = modifier,
        text = "Details screen",
        buttonText = "Close",
        navigateTo = navigateToMainScreen
    )

    Log.i("COMPOSE", "DetailsScreenUI() OUT")
}