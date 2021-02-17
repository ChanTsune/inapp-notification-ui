package com.github.chantsune.inappnotification.animations

/**
 * This interface is a custom listener to determine the end of an animation.
 *
 * @author Phu
 */
internal fun interface AnimationListener {
    /**
     * This method is called when the animation ends.
     *
     * @param animation
     * The Animation object.
     */
    fun onAnimationEnd(animation: Animation?)
}