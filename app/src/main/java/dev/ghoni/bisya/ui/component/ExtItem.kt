package dev.ghoni.bisya.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ghoni.bisya.R
import dev.ghoni.bisya.ui.theme.BisyaTheme

@Composable
fun ExtItem(item: ListItemData, modifier: Modifier = Modifier, onClick: ()-> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Created by: ${item.createdBy}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = "Version: ${item.version}", style = MaterialTheme.typography.bodyMedium)
        }
        when (item.downloadState) {
            DownloadState.NOT_DOWNLOADED -> {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_download),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = modifier
                        .size(IndicatorSize)
                        .clickable {onClick()}
                )
            }

            DownloadState.DOWNLOADING -> {
                Box(
                    modifier = modifier.size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = IndicatorModifier,
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = IndicatorStrokeWidth
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_downward),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = modifier
                            .size(IndicatorSize)
                            .clickable{onClick()}
                    )
                }
            }

            DownloadState.DOWNLOADED -> {
                Button(onClick = onClick) {
                    Text(
                        "Uninstall",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

data class ListItemData(
    val title: String,
    val createdBy: String,
    val version: String,
    var downloadState: DownloadState
)

enum class DownloadState {
    NOT_DOWNLOADED,
    DOWNLOADING,
    DOWNLOADED,
}

private val IndicatorSize = 26.dp
private val IndicatorPadding = 2.dp

private val IndicatorStrokeWidth = IndicatorPadding

private val IndicatorModifier = Modifier
    .size(IndicatorSize)
    .padding(IndicatorPadding)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BisyaTheme {
        ExtItem(
            item = ListItemData(
                title = "SIBI",
                createdBy = "AhmadGhoni",
                version = "1.0.0",
                downloadState = DownloadState.DOWNLOADED
            ),
            onClick = {}
        )
    }
}