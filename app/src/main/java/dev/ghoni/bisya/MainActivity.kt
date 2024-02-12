package dev.ghoni.bisya

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import dev.ghoni.bisya.ui.screen.camera.CameraDemoActivity
import dev.ghoni.bisya.ui.screen.ext.ExtViewModel
import dev.ghoni.bisya.ui.theme.BisyaTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ExtViewModel by viewModels()
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BisyaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BisyaApp(extViewModel = viewModel,onClick = {
                        if (!hasPermissions(this, *permissions)) {
                            ActivityCompat.requestPermissions(this, permissions, 1)
                        } else {
                            startActivity(Intent(this, CameraDemoActivity::class.java))
                        }
                    })
                }
            }
        }
    }

    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context,
                        permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }
}