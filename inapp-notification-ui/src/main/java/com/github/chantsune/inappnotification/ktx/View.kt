package com.github.chantsune.inappnotification.ktx

import android.graphics.Point
import android.view.View

internal val View.locationOnScreen: Point
    get() {
        val locationView = IntArray(2)
        getLocationOnScreen(locationView)
        return Point(locationView[0], locationView[1])
    }
