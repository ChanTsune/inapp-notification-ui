package com.github.chantsune.inappnotification.views

/** Interface that defines the behavior of the main content of a transient bottom bar.  */
internal interface ContentViewCallback {
    /**
     * Animates the content of the transient bottom bar in.
     *
     * @param delay Animation delay.
     * @param duration Animation duration.
     */
    fun animateContentIn(delay: Int, duration: Int)

    /**
     * Animates the content of the transient bottom bar out.
     *
     * @param delay Animation delay.
     * @param duration Animation duration.
     */
    fun animateContentOut(delay: Int, duration: Int)
}
