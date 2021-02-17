package com.github.chantsune.inappnotification.animations

import android.animation.AnimatorSet
import android.animation.TimeInterpolator

/**
 * This interface is implemented only by animation classes that can be combined
 * to animate together.
 *
 */
internal interface Combinable {
    fun animate()
    val animatorSet: AnimatorSet
    fun setInterpolator(interpolator: TimeInterpolator): Animation
    val duration: Long
    fun setDuration(duration: Long): Animation
    fun setListener(listener: AnimationListener?): Animation
}