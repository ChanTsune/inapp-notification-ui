package com.github.chantsune.inappnotification.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

class NotificationUI(c: Context, attrs: AttributeSet? = null) : LinearLayout(c, attrs) {
    private var contentView: View = View(context)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}
