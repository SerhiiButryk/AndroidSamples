package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

@Preview(showSystemUi = true, device = "spec:parent=pixel_c,orientation=portrait")
@Composable
fun SampleUI() {
    MyApplicationTheme {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.padding(10.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column {

                val viewModel = viewModel<SampleViewModel>()
                val sampleData by viewModel.sampleDataFlow.collectAsState(initial = SampleViewModel.SampleData())

                if (sampleData.categoryTitles.isNotEmpty()) {

                    CategoryUI(title = sampleData.categoryTitles[0]) {
                        TextWithButtonItem()
                    }

                    CategoryUI(title = sampleData.categoryTitles[1]) {
                        ColumnItem()
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryUI(title: String, content: @Composable () -> Unit) {
    Column {
        Text(text = title)
        content()
    }
}

/**
 * Weight modifier
 */
@Composable
private fun TextWithButtonItem() {

    val name = "Test name"

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
    ) {

        var expanded by remember { mutableStateOf(false) }

        Row(modifier = Modifier.padding(bottom = if (expanded) 16.dp else 8.dp)) {
            // Allows to place elements relative to each other
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello")
                Text(text = name)
            }
            ElevatedButton(onClick = { expanded = !expanded }) {
                Text(text = "Show more")
            }
        }
    }
}

/**
 * Alignment property for Column
 */
@Composable
private fun ColumnItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary), horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(text = "Hello")
    }
}