package dev.ghoni.bisya.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ghoni.bisya.R
import dev.ghoni.bisya.ui.theme.BisyaTheme

@Composable
fun CameraButton(abjad : String, onClick: () -> Unit, modifier: Modifier = Modifier){
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp, bottom = 20.dp)
            .fillMaxWidth()
            .height(70.dp)
    ){
        Text(
            text = abjad,
            color = Color.White,
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            modifier = modifier,
        )
        Image(
            painter = painterResource(id = R.drawable.ic_capture),
            contentDescription = null,
            modifier = modifier.fillMaxHeight(0.8f),
            contentScale = ContentScale.FillHeight
        )
        Image(
            painter = painterResource(id = R.drawable.ic_switch_camera),
            contentDescription = null,
            modifier = modifier
                .fillMaxHeight(0.5f)
                .clickable { onClick() },
            contentScale = ContentScale.FillHeight
        )
    }
}

@Preview(showBackground = false)
@Composable
fun CameraButtonPreview() {
    BisyaTheme {
        CameraButton("B", {})
    }
}