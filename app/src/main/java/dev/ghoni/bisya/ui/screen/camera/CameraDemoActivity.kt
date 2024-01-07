package dev.ghoni.bisya.ui.screen.camera

import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.pedro.common.ConnectChecker
import com.pedro.encoder.input.video.CameraOpenException
import com.pedro.rtspserver.ClientListener
import com.pedro.rtspserver.RtspServerCamera1
import com.pedro.rtspserver.ServerClient
import dev.ghoni.bisya.R
import dev.ghoni.bisya.core.repository.CameraRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class CameraDemoActivity : AppCompatActivity(), ConnectChecker, View.OnClickListener,
    SurfaceHolder.Callback, ClientListener {
  private val viewModel: CameraViewModel by viewModels()
  private lateinit var rtspServerCamera1: RtspServerCamera1
  private lateinit var button: ImageButton
  private lateinit var bSwitchCamera: ImageButton
  private lateinit var surfaceView: SurfaceView
  private lateinit var tvUrl: TextView
  private lateinit var tvPredict: TextView

  private var currentDateAndTime = ""
  private lateinit var folder: File

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    setContentView(R.layout.activity_camera_demo)
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
    folder = File(storageDir.absolutePath + "/RootEncoder")
    tvUrl = findViewById(R.id.tv_url)
    tvPredict = findViewById(R.id.tv_predict)
    button = findViewById(R.id.b_start_stop)
    button.setOnClickListener(this)
    bSwitchCamera = findViewById(R.id.switch_camera)
    bSwitchCamera.setOnClickListener(this)
    surfaceView = findViewById(R.id.surfaceView)
    rtspServerCamera1 = RtspServerCamera1(surfaceView, this, 1935)
    rtspServerCamera1.streamClient.setClientListener(this)
    surfaceView.holder.addCallback(this)
    supportActionBar?.hide()

    lifecycleScope.launch {
      viewModel.predictionState.collect { predict ->
        updatePredict(predict)
      }
    }

    lifecycleScope.launch {
      viewModel.isClientConnected.collect {isConnect ->
        while (isConnect){
          delay(2000)
          val cameraRepository = CameraRepository()
          viewModel.getPrediction(cameraRepository)
        }
      }
    }
  }

  override fun onNewBitrate(bitrate: Long) {

  }

  override fun onConnectionSuccess() {
    runOnUiThread {
      Toast.makeText(this@CameraDemoActivity, "Connection success", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onConnectionFailed(reason: String) {
    runOnUiThread {
      Toast.makeText(this@CameraDemoActivity, "Connection failed. $reason", Toast.LENGTH_SHORT).show()
      rtspServerCamera1.stopStream()
      button.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN)
    }
  }

  override fun onConnectionStarted(url: String) {
  }

  override fun onDisconnect() {
    runOnUiThread {
      Toast.makeText(this@CameraDemoActivity, "Disconnected", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onAuthError() {
    runOnUiThread {
      Toast.makeText(this@CameraDemoActivity, "Auth error", Toast.LENGTH_SHORT).show()
      rtspServerCamera1.stopStream()
      button.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN)
      tvUrl.text = ""
    }
  }

  override fun onAuthSuccess() {
    runOnUiThread {
      Toast.makeText(this@CameraDemoActivity, "Auth success", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onClick(view: View) {
    when (view.id) {
      R.id.b_start_stop -> if (!rtspServerCamera1.isStreaming) {
        if (rtspServerCamera1.isRecording || rtspServerCamera1.prepareAudio() && rtspServerCamera1.prepareVideo()) {
          button.setColorFilter(ContextCompat.getColor(this, R.color.record), PorterDuff.Mode.SRC_IN)
          rtspServerCamera1.startStream()
          tvUrl.text = rtspServerCamera1.streamClient.getEndPointConnection()
        } else {
          Toast.makeText(this, "Error preparing stream, This device cant do it", Toast.LENGTH_SHORT)
              .show()
        }
      } else {
        button.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN)
        rtspServerCamera1.stopStream()
        tvUrl.text = ""
      }
      R.id.switch_camera -> try {
        rtspServerCamera1.switchCamera()
      } catch (e: CameraOpenException) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
      }
    }
  }

  override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
  }

  override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
    rtspServerCamera1.startPreview()
  }

  override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      if (rtspServerCamera1.isRecording) {
        rtspServerCamera1.stopRecord()
        button.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN)
        Toast.makeText(this, "file " + currentDateAndTime + ".mp4 saved in " + folder.absolutePath, Toast.LENGTH_SHORT).show()
        currentDateAndTime = ""
      }
    }
    if (rtspServerCamera1.isStreaming) {
      rtspServerCamera1.stopStream()
      button.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN)
      tvUrl.text = ""
    }
    rtspServerCamera1.stopPreview()
  }

  override fun onClientConnected(client: ServerClient) {
    runOnUiThread {
      Toast.makeText(this@CameraDemoActivity, "Client connected: ${client.clientAddress}", Toast.LENGTH_SHORT).show()
      viewModel.isClient(true)
    }
  }

  override fun onClientDisconnected(client: ServerClient) {
    runOnUiThread {
      Toast.makeText(this@CameraDemoActivity, "Client disconnected: ${client.clientAddress}", Toast.LENGTH_SHORT).show()
      viewModel.isClient(false)
    }
  }

  private fun updatePredict(prediction: String) {
    tvPredict.text = prediction
  }
}
