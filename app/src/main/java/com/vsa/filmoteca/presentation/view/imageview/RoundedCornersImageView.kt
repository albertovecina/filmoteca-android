package com.vsa.filmoteca.presentation.view.imageview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

import com.vsa.filmoteca.R

/**
 * Created by seldon on 4/04/15.
 */
class RoundedCornersImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val clipPath = Path()
    private val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())

    override fun onDraw(canvas: Canvas) {
        //float radius = 36.0f;
        radius = resources.getDimension(R.dimen.radius_small)
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.clipPath(clipPath)
        super.onDraw(canvas)
    }

    companion object {
        var radius: Float = 0.toFloat()
    }
}
