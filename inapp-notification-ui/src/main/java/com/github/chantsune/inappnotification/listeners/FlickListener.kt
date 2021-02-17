package com.github.chantsune.inappnotification.listeners

import android.view.MotionEvent
import android.view.View


class FlickListener constructor(
    private val listener: Listener,
    private val play: Float = DEFAULT_PLAY
) :
    View.OnTouchListener {
    interface Listener {
        fun onFlickToLeft()
        fun onFlickToRight()
        fun onFlickToUp()
        fun onFlickToDown()
    }

    private var lastX = 0f
    private var lastY = 0f
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDown(event)
            MotionEvent.ACTION_UP -> touchOff(event)
        }
        return true
    }

    private fun touchDown(event: MotionEvent) {
        lastX = event.x
        lastY = event.y
    }

    private fun touchOff(event: MotionEvent) {
        val currentX = event.x
        val currentY = event.y
        when {
            currentY + play < lastY -> {
                listener.onFlickToUp()
            }
            lastY < currentY - play -> {
                listener.onFlickToDown()
            }
            currentX + play < lastX -> {
                listener.onFlickToLeft()
            }
            lastX < currentX - play -> {
                listener.onFlickToRight()
            }
        }
    }

    companion object {
        private const val DEFAULT_PLAY = 100f
    }
}