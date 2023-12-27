package dev.ghoni.bisya.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.ghoni.bisya.ui.component.AppBar
import dev.ghoni.bisya.ui.component.DownloadState
import dev.ghoni.bisya.ui.component.ExtItem
import dev.ghoni.bisya.ui.component.ListItemData
import dev.ghoni.bisya.ui.theme.BisyaTheme

@Composable
fun ExtScreen(
    title : String,
    modifier: Modifier = Modifier
){
    val data = listOf(
        ListItemData("Title 1", "Author 1", "1.0", DownloadState.NOT_DOWNLOADED),
        ListItemData("Title 2", "Author 2", "2.0", DownloadState.NOT_DOWNLOADED),
        ListItemData("Title 3", "Author 3", "3.0", DownloadState.NOT_DOWNLOADED)
    )

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AppBar(title)
        LazyColumn {
            items(data) { item ->
                ExtItem(item, onClick = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExtScreenPreview() {
    BisyaTheme {
        ExtScreen("Extensions")
    }
}