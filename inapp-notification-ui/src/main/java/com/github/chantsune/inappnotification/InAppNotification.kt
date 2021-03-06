package com.github.chantsune.inappnotification

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.github.chantsune.inappnotification.animations.Animation
import com.github.chantsune.inappnotification.animations.SlideInAnimation
import com.github.chantsune.inappnotification.animations.SlideOutAnimation
import com.github.chantsune.inappnotification.views.NotificationContainerLayout
import android.os.Handler
import com.github.chantsune.inappnotification.listeners.FlickListener

public class InAppNotification private constructor(
    private val context: Context,
    private val targetParent: ViewGroup,
    internal val view: NotificationContainerLayout,
) {

    public var stayingDuration: Long = DEFAULT_STAYING_DURATION
        private set
    public var animationDuration: Long = DEFAULT_ANIMATION_DURATION
        private set

    internal val handler = Handler(Looper.getMainLooper())

    internal fun setContentView(contentView: View): InAppNotification {
        view.removeAllViews()
        view.addView(contentView)
        return this
    }
    public fun setStayingDuration(milliseconds: Long): InAppNotification {
        stayingDuration = milliseconds
        return this
    }
    public fun setAnimationDuration(milliseconds: Long): InAppNotification {
        animationDuration = milliseconds
        return this
    }

    public fun setContentView(resource: Int): InAppNotification {
        view.removeAllViews()
        LayoutInflater.from(view.context).inflate(resource, view)
        return this
    }

    public fun show() {
        showView()
    }

    private fun showView() {
        if (view.parent == null) {
            view.visibility = View.INVISIBLE
            targetParent.addView(view)
        }
        showViewImpl()
    }

    private fun showViewImpl() {
        if (shouldAnimate()) {
            animateViewIn()
        } else {
            if (view.parent != null) {
                view.visibility = View.VISIBLE
            }
//            onViewShown()
        }
    }

    private fun shouldAnimate(): Boolean {
        return true
    }

    private fun animateViewIn() {
        view.post {
            if (view.parent != null) {
                view.visibility = View.VISIBLE
            }
            startSlideInAnimation()
        }
    }

    private fun startSlideInAnimation() {
        SlideInAnimation(view)
            .setDuration(animationDuration)
            .setDirection(Animation.DIRECTION_UP)
            .setListener {
                handler.postDelayed(runSlideOut, stayingDuration)
            }
            .animate()
    }

    private fun startSlideOutAnimation() {
        SlideOutAnimation(view)
            .setDuration(animationDuration)
            .setDirection(Animation.DIRECTION_UP)
            .setListener {
                hideView()
//                onViewHide()
            }
            .animate()
    }

    private fun hideView() {
        view.visibility = View.GONE
        targetParent.removeView(view)
    }

    private val runSlideOut: Runnable = Runnable {
        startSlideOutAnimation()
    }


    public companion object {

        private const val DEFAULT_ANIMATION_DURATION: Long = 1000
        private const val DEFAULT_STAYING_DURATION: Long = 2000

        public fun make(view: View): InAppNotification {
            val parent = findSuitableParent(view)
                ?: throw IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.")
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val container = inflater.inflate(
                R.layout.inapp_notification_default_content_view,
                parent,
                false
            ) as? NotificationContainerLayout
                ?: throw IllegalStateException("Internal Layout container was inflated.")
            // context, parent, content, content
            return InAppNotification(context, parent, container).apply {
                this.view.setOnTouchListener(FlickListener(object: FlickListener.Listener {
                    override fun onFlickToLeft() {}
                    override fun onFlickToRight() {}
                    override fun onFlickToUp() {
                        handler.removeCallbacks(runSlideOut)
                        startSlideOutAnimation()
                    }
                    override fun onFlickToDown() {}

                }))

            }
        }

        private fun findSuitableParent(view: View): ViewGroup? {
            var view: View? = view
            var fallback: ViewGroup? = null
            do {
                if (view is CoordinatorLayout) {
                    // We've found a CoordinatorLayout, use it
                    return view
                } else if (view is FrameLayout) {
                    if (view.id == android.R.id.content) {
                        // If we've hit the decor content view, then we didn't find a CoL in the
                        // hierarchy, so use it.
                        return view
                    } else {
                        // It's not the content view but we'll use it as our fallback
                        fallback = view
                    }
                }
                if (view != null) {
                    // Else, we will loop and crawl up the view hierarchy and try to find a parent
                    val parent = view.parent
                    view = if (parent is View) parent else null
                }
            } while (view != null)

            // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
            return fallback
        }
    }
}
