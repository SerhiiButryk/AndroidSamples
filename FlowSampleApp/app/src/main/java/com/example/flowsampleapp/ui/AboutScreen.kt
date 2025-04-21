package com.example.flowsampleapp.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flowsampleapp.ui.theme.MainAppTheme

@Composable
fun AboutScreen(modifier: Modifier = Modifier, navigateBack: () -> Unit = {}) {
    MainAppTheme {
        AboutScreenUI(modifier, navigateBack)
    }
}

@Preview("Default", showSystemUi = true)
@Preview("Default", showSystemUi = true, device = "spec:parent=pixel_5,orientation=landscape")
@Preview("Dark mode", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AboutScreenUIPreview() {
    MainAppTheme {
        AboutScreenUI()
    }
}

@Composable
private fun AboutScreenUI(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {}
) {
    StartUI(
        modifier = modifier,
        text = "About screen",
        buttonText = "Close",
        navigateTo = navigateBack
    )
}