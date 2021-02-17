package com.github.chantsune.inappnotification.demo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.chantsune.inappnotification.InAppNotification


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        findViewById<Button>(R.id.button).also { button ->
            button.setOnClickListener {
//                Snackbar.make(button, "Text", 1000).show()
//                return@setOnClickListener
                InAppNotification.make(button).apply {
                    stayingDuration = 4000
                    setContentView(R.layout.view_sample)
                }.show()
            }
        }
    }
}
