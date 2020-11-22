package com.vsa.filmoteca.presentation.view.dialog.progress

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.annotation.RequiresApi
import com.vsa.filmoteca.R
import kotlinx.android.synthetic.main.dialog_loading.*
import kotlin.math.hypot

class LoadingDialog(context: Context) :
        Dialog(context, R.style.FloatingDialog) {

    init {
        setCancelable(false)
        setOnCancelListener(null)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private val fadeInDuration: Long = 200
    private val fadeOutDuration: Long = 200

    private var isHiding: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
        )

        viewBackground.startAnimation(AlphaAnimation(0f, viewBackground.alpha).apply {
            duration = fadeInDuration
        })
    }

    fun dismissWithAnimation() {
        if (!isHiding) {
            isHiding = true
            viewBackground.startAnimation(AlphaAnimation(viewBackground.alpha, 0f).apply {
                duration = fadeOutDuration
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        super@LoadingDialog.dismiss()
                        isHiding = false
                    }

                    override fun onAnimationStart(animation: Animation?) {

                    }
                })
            })
        }
    }

    fun showError(title: String?, message: String?, button: String, onClick: (() -> Unit) = {}) {
        title?.let {
            textViewDialogTitle.visibility = View.VISIBLE
            textViewDialogTitle.text = title
        }
        message?.let {
            textViewDialogDescription.visibility = View.VISIBLE
            textViewDialogDescription.text = message
        }
        buttonOk.text = button
        buttonOk.setOnClickListener {
            dismissWithAnimation()
            onClick()
        }

        showErrorWithCircularReveal()

    }

    private fun showErrorWithCircularReveal() {
        val cx = wrapperErrorMessage.width / 2
        val cy = wrapperErrorMessage.height / 2

        // get the final radius for the clipping circle
        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        // create the animator for this view (the start radius is zero)
        val anim =
                ViewAnimationUtils.createCircularReveal(wrapperErrorMessage, cx, cy, 0f, finalRadius)

        // make the view visible and start the animation
        wrapperErrorMessage.visibility = View.VISIBLE
        anim.start()
    }

}