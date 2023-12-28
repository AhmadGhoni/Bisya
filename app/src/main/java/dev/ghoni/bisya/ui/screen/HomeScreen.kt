package dev.ghoni.bisya.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.ghoni.bisya.R
import dev.ghoni.bisya.ui.component.AppBar
import dev.ghoni.bisya.ui.component.HomeButton
import dev.ghoni.bisya.ui.component.QuoteCard
import dev.ghoni.bisya.ui.navigation.Screen
import dev.ghoni.bisya.ui.theme.BisyaTheme

@Composable
fun HomeScreen(
    title : String,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column {
        AppBar(title)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_home),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .padding(start = 50.dp, end = 50.dp),
                    contentScale = ContentScale.FillHeight
                )
                val quoteText = stringResource(id = R.string.quote_home)
                QuoteCard(quoteText = quoteText)
            }
            HomeButton {
                navController.navigate(Screen.Camera.route)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BisyaTheme {
        HomeScreen("Home", navController = rememberNavController())
    }
}