package com.first.hdz.ninelock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


/**
 * created by hdz
 * on 2018/8/7
 */
public class ToggleView extends View {

    //最好能添加触感反馈

    private Paint inPaint;
    private Paint outPaint;
    private int outColor;
    private int inColor;
    private int outR;
    private int inR;
    private boolean isChecked;

    public int getOutColor() {
        return outColor;
    }

    public void setOutColor(int outColor) {
        this.outColor = outColor;
    }

    public int getInColor() {
        return inColor;
    }

    public void setInColor(int inColor) {
        this.inColor = inColor;
    }

    public int getOutR() {
        return outR;
    }

    public void setOutR(int outR) {
        this.outR = outR;
    }

    public int getInR() {
        return inR;
    }

    public void setInR(int inR) {
        this.inR = inR;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public ToggleView(Context context) {
        this(context, null);
    }

    public ToggleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ToggleView, defStyleAttr, 0);
        int indexCount = array.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.ToggleView_InCircleR_T:
                    inR = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(Dimension.DP, 10, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ToggleView_OutCircleR_T:
                    outR = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(Dimension.DP, 50, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ToggleView_InCircleColor_T:
                    inColor = array.getColor(attr, 0xff00ffff);
                    break;
                case R.styleable.ToggleView_OutCircleColor_T:
                    outColor = array.getColor(attr, 0xff888888);
                    break;
            }
        }
        inPaint = new Paint();
        inPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        inPaint.setColor(inColor);
        inPaint.setAntiAlias(true);

        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setStrokeWidth(5);
        outPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        outPaint.setStyle(Paint.Style.FILL);
        outPaint.setColor(Color.WHITE);
        canvas.drawCircle(cx, cy, outR, outPaint);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setColor(outColor);
        canvas.drawCircle(cx, cy, outR, outPaint);
        canvas.drawCircle(cx, cy, inR, inPaint);

    }
}
