package com.github.chantsune.inappnotification.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.github.chantsune.inappnotification.animations.Animation
import com.github.chantsune.inappnotification.animations.SlideInAnimation
import com.github.chantsune.inappnotification.animations.SlideOutAnimation
import com.github.chantsune.inappnotification.listeners.FlickListener

internal class NotificationContainerLayout(c: Context, attrs: AttributeSet? = null) :
    LinearLayout(c, attrs) {

    init {
        setOnTouchListener(FlickListener(object: FlickListener.Listener {
            override fun onFlickToLeft() {
            }

            override fun onFlickToRight() {
            }

            override fun onFlickToUp() {
                handler.removeCallbacks(runSlideOut)
                startSlideOut()
            }

            override fun onFlickToDown() {
            }

        }))
    }

    private val runSlideOut: Runnable = Runnable {
        startSlideOut()
    }

    fun startSlideIn(stayingDuration: Long) {
        SlideInAnimation(this)
            .setDirection(Animation.DIRECTION_UP)
            .setListener {
                handler.postDelayed(runSlideOut, stayingDuration)
            }.animate()

    }

    fun startSlideOut() {
        SlideOutAnimation(this)
            .setDirection(Animation.DIRECTION_UP)
            .animate()
    }
}
