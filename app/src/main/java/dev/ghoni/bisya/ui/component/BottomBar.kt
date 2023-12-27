package dev.ghoni.bisya.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import dev.ghoni.bisya.R
import dev.ghoni.bisya.ui.navigation.BottomBarItem
import dev.ghoni.bisya.ui.navigation.BottomNavItem
import dev.ghoni.bisya.ui.theme.BisyaTheme

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    currentScreen: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit,
) {
    NavigationBar {
        val bottomNavigation = listOf(
            BottomBarItem(
                title = stringResource(id = R.string.txt_home),
                icon = Icons.Outlined.Home,
                screen = BottomNavItem.Home
            ),
            BottomBarItem(
                title = stringResource(id = R.string.txt_ext),
                icon = ImageVector.vectorResource(id = R.drawable.ic_ext),
                screen = BottomNavItem.Ext
            ),
            BottomBarItem(
                title = stringResource(id = R.string.txt_profile),
                icon = Icons.Outlined.Person,
                screen = BottomNavItem.Profile
            )
        )
        NavigationBar {
            bottomNavigation.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.title) },
                    label = { Text(item.title) },
                    selected = item.screen == currentScreen,
                    onClick = { onItemSelected(item.screen) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BisyaTheme {
        BottomBar(currentScreen = BottomNavItem.Home, onItemSelected = {})
    }
}