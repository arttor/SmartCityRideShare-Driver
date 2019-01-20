package com.tlabs.smartcity.rideshare.ridesharedriver.util

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

object AnimationUtil {
    fun fadeOut(content: View, duration: Long = 300, startDelay: Long = 0, hide: Boolean = true, transparency: Float = 1f) {
        content.alpha = transparency
        val animator = ObjectAnimator.ofFloat(content, "alpha", transparency, 0f)
        animator.startDelay = startDelay
        animator.duration = duration
        if (hide)
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    content.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        animator.start()
    }

    fun fadeIn(content: View, startDelay: Long = 0, duration: Long = 300, hide: Boolean = true, transparency: Float = 1f) {
        content.alpha = 0f
        if (hide)
            content.visibility = View.VISIBLE
        val animator = ObjectAnimator.ofFloat(content, "alpha", 0f, transparency)
        animator.startDelay = startDelay
        animator.duration = duration
        animator.start()
    }
}
