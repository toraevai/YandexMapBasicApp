package com.example.yandexmapbasicapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.yandexmapbasicapp.ui.theme.YandexMapBasicAppTheme

@Composable
fun ServicesListScreen(
    listOfServicesToDisplay: List<String>,
    onCheckedChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listOfServices = listOf("a", "b", "c")

    Column(modifier = modifier) {
        for (service in listOfServices) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Service $service")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = listOfServicesToDisplay.contains(service),
                    onCheckedChange = { onCheckedChange(service) }
                )
            }
        }
    }
}

@Preview
@Composable
fun ServicesListScreenPreview() {
    val services = mutableListOf("a", "b")
    YandexMapBasicAppTheme {
        ServicesListScreen(listOfServicesToDisplay = services, onCheckedChange = {})
    }
}