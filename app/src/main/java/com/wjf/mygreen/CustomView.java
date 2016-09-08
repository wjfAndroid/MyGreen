package com.wjf.mygreen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CustomView extends View {
Drawable mDrawable;
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawable = context.getDrawable(R.drawable.min);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint_green = getPaint(3, Color.GREEN);
        Paint paint_red = getPaint(3, Color.RED);
        Paint paint_grey = getPaint(3, Color.GRAY);

//        canvas.drawRect(10, 10, 100, 100, paint_green);
//        canvas.save();
//        canvas.rotate(45);
//        canvas.drawRect(110, 110, 200, 200, paint_red);
//        canvas.restore();
//        canvas.save();
//
//        canvas.translate(100, 0);
//        canvas.drawRect(10, 10, 100, 100,paint_grey);
//        canvas.restore();
//
//        canvas.drawRect(10,10,200,200,paint_green);
        mDrawable.setBounds(10,10,66,318);

        mDrawable.draw(canvas);




    }

    public Paint getPaint(int width, int color) {
        Paint paint = new Paint();
        paint.setStrokeWidth(width);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }
}
