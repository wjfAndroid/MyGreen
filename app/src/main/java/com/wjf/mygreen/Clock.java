package com.wjf.mygreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/9/6.
 */
public class Clock extends View {
    private Time mCalender;

    private Drawable dia;
    private Drawable hour;
    private Drawable min;

    private int mDiaWidth;
    private int mDiaHeight;

    private boolean mAttach;
    private boolean mSizeChanged;

    private float mHour;
    private float mMinute;

    public Clock(Context context) {
        this(context, null);
    }

    public Clock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Resources r = context.getResources();
        if (dia == null) {
            dia = r.getDrawable(R.drawable.biaopan);
        }
        if (hour == null) {
            hour = r.getDrawable(R.drawable.hour);
        }
        if (min == null) {
            min = r.getDrawable(R.drawable.min);
        }
        mCalender = new Time();
        GregorianCalendar calendar = new GregorianCalendar();

        mDiaWidth = dia.getIntrinsicWidth();
        mDiaHeight = dia.getIntrinsicHeight();
    }

//    public Clock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//
//
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float hScale = 1.0f;
        float wScale = 1.0f;
        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mDiaWidth) {
            wScale = (float) widthSize / (float) mDiaWidth;
        }
        if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mDiaHeight) {
            hScale = (float) heightSize / (float) mDiaHeight;
        }
        float scale = Math.min(wScale, hScale);


        setMeasuredDimension(
                resolveSizeAndState((int) (scale * mDiaWidth), widthMeasureSpec, 0),
                resolveSizeAndState((int) (scale * mDiaHeight), heightMeasureSpec, 0));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSizeChanged = true;
    }

    private void onTimeChanged() {
        mCalender.setToNow();
        int hour = mCalender.hour;
        int minute = mCalender.minute;
        int second = mCalender.second;

        mMinute = minute + second / 60.0f;
        mHour = hour + minute / 60.0f;
        mSizeChanged = true;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                String tz = intent.getStringExtra("time-zone");
                mCalender = new Time(TimeZone.getTimeZone(tz).getID());
            }

            onTimeChanged();

            invalidate();
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAttach) {
            mAttach = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_CHANGED);
            filter.addAction(Intent.ACTION_TIME_TICK);
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

            getContext().registerReceiver(mReceiver, filter);

            mCalender = new Time();

            onTimeChanged();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttach) {
            getContext().unregisterReceiver(mReceiver);
            mAttach = false;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boolean change = mSizeChanged;
        if (change) {
            mSizeChanged = false;
        }
        int avilableWidth = super.getRight() - super.getLeft();
        int avilableHeight = super.getBottom() - super.getTop();

        int x = avilableWidth / 2;
        int y = avilableHeight / 2;

        Drawable dial = dia;
        int w = dial.getIntrinsicWidth();
        int h = dial.getIntrinsicHeight();
        boolean scale = false;

        if (w > avilableWidth || h > avilableHeight) {
            scale = true;
            float s = Math.min((float) avilableWidth / (float) w, (float) avilableHeight / (float) h);
            canvas.save();
            canvas.scale(s, s, x, y);
        }

        if (change) {
            dial.setBounds(x - (w / 2), 0, x + (w / 2),h);
        }
        dial.draw(canvas);
        canvas.save();

        canvas.rotate(mHour / 12.0f * 360.0f, x, y);
        Drawable hourhand = hour;
        if (change) {
            w = hourhand.getIntrinsicWidth();
            h = hourhand.getIntrinsicHeight();
            hourhand.setBounds(x - (w / 2),0, x + (w / 2),h);
        }
        hourhand.draw(canvas);
        canvas.restore();

        canvas.save();

        canvas.rotate(mMinute / 60.0f * 360.0f, x, y);
        Drawable minhand = min;
        if (change) {
            w = minhand.getIntrinsicWidth();
            h = minhand.getIntrinsicHeight();
            minhand.setBounds(x - (w / 2), 0, x + (w / 2), h );
        }
        minhand.draw(canvas);
        canvas.save();

        if (scale) {
            canvas.restore();
        }


    }
}
