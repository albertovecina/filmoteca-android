package com.vsa.filmoteca.view.imageview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ImageView

import com.vsa.filmoteca.R

/**
 * Created by seldon on 4/04/15.
 */
class RoundedCornersImageView : ImageView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

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
