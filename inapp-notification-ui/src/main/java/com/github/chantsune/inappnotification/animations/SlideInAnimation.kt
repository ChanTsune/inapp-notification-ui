package com.github.chantsune.inappnotification.animations

import android.animation.*
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
/**
 * This animation causes the view to slide in from the borders of the screen.
 *
 * @author SiYao
 */
/**
 * This animation causes the view to slide in from the borders of the
 * screen.
 *
 * @param view
 * The view to be animated.
 */
internal class SlideInAnimation(override var view: View) : Animation(),
    Combinable {
    /**
     * The available directions to slide in from are `DIRECTION_LEFT`
     * , `DIRECTION_RIGHT`, `DIRECTION_TOP` and
     * `DIRECTION_BOTTOM`.
     *
     * @return The direction to slide the view in from.
     * @see Animation
     */
    var direction: Int = DIRECTION_LEFT

    /**
     * @return The interpolator of the entire animation.
     */
    var interpolator: TimeInterpolator = AccelerateDecelerateInterpolator()

    /**
     * @return The duration of the entire animation.
     */
    override var duration: Long = DURATION_LONG.toLong()

    /**
     * @return The listener for the end of the animation.
     */
    var listener: AnimationListener? = null


    override fun animate() {
        animatorSet.start()
    }

    override val animatorSet: AnimatorSet
        get() {
            var parentView = view.parent as ViewGroup
            val rootView = view.rootView as ViewGroup
            while (parentView != rootView) {
                parentView.clipChildren = false
                parentView = parentView.parent as ViewGroup
            }
            rootView.clipChildren = false
            val locationView = IntArray(2)
            view.getLocationOnScreen(locationView)
            var slideAnim: ObjectAnimator? = null
            when (direction) {
                DIRECTION_LEFT -> slideAnim = ObjectAnimator.ofFloat(
                    view,
                    View.X,
                    (-locationView[0] - view.width).toFloat(),
                    view.x
                )
                DIRECTION_RIGHT -> slideAnim =
                    ObjectAnimator.ofFloat(view, View.X, rootView.right.toFloat(), view.x)
                DIRECTION_UP -> slideAnim = ObjectAnimator.ofFloat(
                    view,
                    View.Y,
                    (-locationView[1] - view.height).toFloat(),
                    view.y
                )
                DIRECTION_DOWN -> slideAnim =
                    ObjectAnimator.ofFloat(view, View.Y, rootView.bottom.toFloat(), view.y)
                else -> {
                }
            }
            val slideSet = AnimatorSet()
            slideSet.play(slideAnim)
            slideSet.interpolator = interpolator
            slideSet.duration = duration
            slideSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {
                    listener?.onAnimationEnd(this@SlideInAnimation)
                }
            })
            return slideSet
        }

    /**
     * The available directions to slide in from are `DIRECTION_LEFT`
     * , `DIRECTION_RIGHT`, `DIRECTION_TOP` and
     * `DIRECTION_BOTTOM`.
     *
     * @param direction
     * The direction to set to slide the view in from.
     * @return This object, allowing calls to methods in this class to be
     * chained.
     * @see Animation
     */
    fun setDirection(direction: Int): SlideInAnimation {
        this.direction = direction
        return this
    }

    /**
     * @param interpolator
     * The interpolator of the entire animation to set.
     */
    override fun setInterpolator(interpolator: TimeInterpolator): SlideInAnimation {
        this.interpolator = interpolator
        return this
    }

    /**
     * @param duration
     * The duration of the entire animation to set.
     * @return This object, allowing calls to methods in this class to be
     * chained.
     */
    override fun setDuration(duration: Long): SlideInAnimation {
        this.duration = duration
        return this
    }

    /**
     * @param listener
     * The listener to set for the end of the animation.
     * @return This object, allowing calls to methods in this class to be
     * chained.
     */
    override fun setListener(listener: AnimationListener?): SlideInAnimation {
        this.listener = listener
        return this
    }

}