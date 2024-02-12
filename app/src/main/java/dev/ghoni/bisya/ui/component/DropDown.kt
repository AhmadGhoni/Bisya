package dev.ghoni.bisya.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.ghoni.bisya.ui.screen.ext.ExtViewModel

@Composable
fun DropDown(modifier: Modifier = Modifier,viewModel: ExtViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("SIBI Alfabet") }

    val data by viewModel.data.collectAsState()
    val filteredItems = remember(data) {
        data.filter { it.downloadState == DownloadState.DOWNLOADED }
    }

    Column(
        modifier.shadow(0.5.dp).padding(10.dp)
    ) {
        Text(
            text = "Pilih Model",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        ) {
            Text(
                text = selectedOption,
                modifier = Modifier.padding(start = 2.dp, top = 8.dp, bottom = 8.dp)
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown arrow",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 2.dp, top = 8.dp, bottom = 8.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            filteredItems.forEach { item ->
                DropdownMenuItem(text = { Text(item.title) }, onClick = {
                    selectedOption = item.title
                    expanded = false
                })
            }
        }
    }
}