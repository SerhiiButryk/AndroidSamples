package com.example.flowsampleapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StartUI(
    modifier: Modifier = Modifier,
    text: String,
    buttonText: String,
    navigateTo: () -> Unit = {}
) {

    Log.i("COMPOSE", "StartUI() $text IN")

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
            Button(onClick = { navigateTo() }) {
                Text(buttonText)
            }
        }
    }

    Log.i("COMPOSE", "StartUI() $text OUT")
}