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

class InAppNotification private constructor(
    private val context: Context,
    private val targetParent: ViewGroup,
    private val view: NotificationContainerLayout,
) {

    var stayingDuration: Long = 2000

    fun setContentView(contentView: View): InAppNotification {
        view.removeAllViews()
        view.addView(contentView)
        return this
    }
    fun setContentView(resource: Int): InAppNotification {
        val inflater = LayoutInflater.from(view.context).inflate(resource, view)
        return this
    }

    fun show() {
        showView()
    }

    private fun showView() {

        if (view.parent == null) {
            val lp = view.layoutParams
            if (lp is CoordinatorLayout.LayoutParams) {
                // setUpBehavior(lp)
            }

//            extraBottomMarginAnchorView = calculateBottomMarginForAnchorView()
//            updateMargins()

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

    private fun shouldAnimate():Boolean {
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
        view.startSlideIn(stayingDuration)
//        SlideInAnimation(view)
//            .setDirection(Animation.DIRECTION_UP)
//            .setListener {
//                Handler(Looper.getMainLooper()).postDelayed({
//                    startSlideOutAnimation()
//                }, stayingDuration)
//            }
//            .animate()
    }

    private fun startSlideOutAnimation() {
//        SlideOutAnimation(view)
//            .setDirection(Animation.DIRECTION_UP)
//            .setListener {
//                hideView()
////                onViewHide()
//            }
//            .animate()
    }

    private fun hideView() {
        view.visibility = View.GONE
        targetParent.removeView(view)
    }


    companion object {
        fun make(view: View): InAppNotification {
            val parent = findSuitableParent(view)
                ?: throw IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.")
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val container = inflater.inflate(R.layout.inapp_notification_default_content_view, parent, false) as? NotificationContainerLayout
                ?: throw IllegalStateException("Internal Layout container was inflated.")
            // context, parent, content, content
            return InAppNotification(context, parent, container)
        }

        private fun findSuitableParent(view: View): ViewGroup? {
            var view: View? = view
            var fallback: ViewGroup? = null
            do {
                if (view is CoordinatorLayout) {
                    // We've found a CoordinatorLayout, use it
                    return view
                } else if (view is FrameLayout) {
                    if (view.getId() == R.id.content) {
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
