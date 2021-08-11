package com.applicaionsignaling.service

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import com.applicaionsignaling.R

class SignalingService: Service() {

    private var isPlay = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        val manager = getSystemService(WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 0

        val rootView = LayoutInflater.from(this).inflate(
            R.layout.signaling_layout, null
        )

        val buttonPlay = rootView.findViewById<Button>(R.id.playButton)
        var mediaPlayer = MediaPlayer.create(baseContext, R.raw.signal)

        val handler = Handler(Looper.getMainLooper())
        val delay = 2000

        val runnable: Runnable = object : Runnable {
            override fun run() {
                mediaPlayer?.start()
                handler.postDelayed(this, delay.toLong())
            }
        }

        buttonPlay.setOnClickListener {
            if (!isPlay){
                if (mediaPlayer==null) {
                    mediaPlayer = MediaPlayer.create(baseContext, R.raw.signal)
                }
                handler.postDelayed(runnable, 0)
                buttonPlay.text = "Stop"
                isPlay = true
            }else{
                buttonPlay.text = "Play"
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                handler.removeCallbacks(runnable)
                isPlay = false
            }
        }
        manager.addView(rootView, params)
    }

}