package com.github.chantsune.inappnotification.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.chantsune.inappnotification.animations.Animation
import com.github.chantsune.inappnotification.animations.AnimationListener
import com.google.android.material.snackbar.Snackbar
import com.github.chantsune.inappnotification.animations.SlideInAnimation
import com.github.chantsune.inappnotification.animations.SlideOutAnimation


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        findViewById<Button>(R.id.button).also { button ->
            button.setOnClickListener {
                Snackbar.make(button, "Text", 1000).show()
                SlideInAnimation(button)
                    .setDirection(Animation.DIRECTION_UP)
                    .setListener(object :
                        AnimationListener {
                        override fun onAnimationEnd(animation: Animation?) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                SlideOutAnimation(
                                    button
                                ).setDirection(Animation.DIRECTION_UP).animate()
                            }, 4000)
                        }

                    })
                    .animate()
            }
        }
    }
}
