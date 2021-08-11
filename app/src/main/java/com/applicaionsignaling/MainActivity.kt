package com.applicaionsignaling

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.applicaionsignaling.dialog.AttentionDialog
import com.applicaionsignaling.service.SignalingService

class MainActivity : AppCompatActivity() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var buttonStart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart = findViewById(R.id.startButton)

        buttonStart.setOnClickListener {
            startService(Intent(this, SignalingService::class.java))
            finish()
        }

        if (Settings.canDrawOverlays(this))
            buttonStart.isVisible = true
        else{
            val customDialog = AttentionDialog(onClickDialog)
            customDialog.show(supportFragmentManager, "AttentionDialog")
            createResultLauncher()
        }

    }

    private fun createResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (Settings.canDrawOverlays(this))
                    buttonStart.isVisible = true
                else {
                    val customDialog = AttentionDialog(onClickDialog)
                    customDialog.show(supportFragmentManager, "AttentionDialog")
                }
            }
    }
    private fun openActivityForResult() {
        val intent =
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        resultLauncher.launch(intent)

    }

    private val onClickDialog: () -> Unit = { openActivityForResult() }
}