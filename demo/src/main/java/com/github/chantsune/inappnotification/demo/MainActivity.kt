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
                InAppNotification.make(button)
                    .setAnimationDuration(500)
                    .setStayingDuration(4000)
                    .setContentView(R.layout.view_sample)
                    .show()
            }
        }
    }
}
