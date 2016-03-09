package com.vsa.filmoteca.view.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.vsa.filmoteca.R;

/**
 * Created by seldon on 4/04/15.
 */
public class RoundedCornersImageView extends ImageView {
    public static float radius;

    public RoundedCornersImageView(Context context) {
        super(context);
    }

    public RoundedCornersImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedCornersImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //float radius = 36.0f;
        radius = getResources().getDimension(R.dimen.radius_small);
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
