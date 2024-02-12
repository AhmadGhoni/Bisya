package dev.ghoni.bisya.ui.screen.ext

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.ghoni.bisya.ui.component.AppBar
import dev.ghoni.bisya.ui.component.ExtItem
import dev.ghoni.bisya.ui.theme.BisyaTheme

@Composable
fun ExtScreen(
    title: String,
    modifier: Modifier = Modifier,
    viewModel: ExtViewModel
) {
    val data by remember { viewModel.data }.collectAsState()
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AppBar(title)
        LazyColumn {
            items(data, key = { item -> item.title }) { item ->
                ExtItem(
                    item,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExtScreenPreview() {
    BisyaTheme {
        ExtScreen("Extensions", viewModel = ExtViewModel())
    }
}