package dev.ghoni.bisya.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ghoni.bisya.ui.component.AppBar
import dev.ghoni.bisya.ui.component.DropDown
import dev.ghoni.bisya.ui.screen.ext.ExtViewModel
import dev.ghoni.bisya.ui.theme.BisyaTheme

@Composable
fun SettingsScreen(
    title : String,
    modifier: Modifier = Modifier,
    viewModel: ExtViewModel
) {
    Column(
        modifier = modifier
    ){
        AppBar(title)
        DropDown(viewModel = viewModel)
        Column(
           modifier = modifier.padding(10.dp)
        ) {
            Text(
                text = "Versi Aplikasi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "1.0",
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(start = 2.dp,top = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    BisyaTheme {
        SettingsScreen(title = "Settings", viewModel = ExtViewModel())
    }
}