package com.first.hdz.ninelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * created by hdz
 * on 2018/8/7
 */
public class NineGroupView extends ViewGroup {

    private OnFinishListener mListener;

    public interface OnFinishListener {

        public void onFinish(List<Integer> positionSet);
    }

    public void setOnFinishListener(OnFinishListener listener) {
        this.mListener = listener;
    }

    private Paint paint;
    private Path path;
    private TreeMap<Integer, Boolean> checkedMap;
    private List<Integer> checkedINdexSet;   //用于记录被选中的序号排列。
    private List<Point> positionList;

    private List<Point> childSize = new ArrayList<>();

    public NineGroupView(Context context) {
        this(context, null);
    }

    public NineGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void reset() {
        for (int i = 0; i < 9; i++) {
            checkedMap.put(i, false);
        }
        checkedINdexSet.clear();
        IsDownIn = false;
        IsUp = false;
        path.reset();
        prePoint = new Point(-1, -1);
        currentPoint = new Point(-1, -1);
        invalidate();
    }

    private void init() {
        checkedMap = new TreeMap<>();
        for (int i = 0; i < 9; i++) {
            checkedMap.put(i, false);
        }
        checkedINdexSet = new ArrayList<>();
        positionList = new ArrayList<>();
        path = new Path();
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        setWillNotDraw(false);
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        positionList = PositionUtils.getNinePoints(width, height);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            Point size = childSize.get(i);
            Point position = positionList.get(i);
            int cLeft = position.x - size.x;
            int cTop = position.y - size.y;
            int cRight = position.x + size.x;
            int cBottom = position.y + size.y;
            child.layout(cLeft, cTop, cRight, cBottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            Point point = new Point();
            point.x = child.getMeasuredWidth();
            point.y = child.getMeasuredHeight();
            childSize.add(point);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private boolean IsDownIn = false;
    private boolean IsUp = false;

    private Point prePoint = new Point(-1, -1);
    private Point currentPoint = new Point(-1, -1);

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (IsUp) {
                    return true;
                }
                MarkBean bean = isInToggle(new Point(currentX, currentY));
                if (bean != null) {
                    IsDownIn = true;
                    prePoint = positionList.get(bean.getIndex());
                    path.moveTo(prePoint.x, prePoint.y);
                    invalidate();
                }
            }
            break;
            case MotionEvent.ACTION_UP:
                IsUp = true;
                if (IsDownIn) {
                    currentPoint = prePoint;
                    IsDownIn = false;
                    invalidate();
                    if (mListener != null) {
                        mListener.onFinish(checkedINdexSet);
                        reset();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: {
                if (IsDownIn) {
                    if (!IsUp) {
                        MarkBean bean = isInToggle(new Point(currentX, currentY));
                        if (bean != null) {
                            int index = bean.getIndex();
                            currentPoint = positionList.get(index);
                            path.lineTo(currentPoint.x, currentPoint.y);
                            invalidate();
                            prePoint = currentPoint;
                        } else {
                            currentPoint = new Point(currentX, currentY);
                            invalidate();
                        }
                    }
                } else {
                    if (!IsUp) {
                        MarkBean bean = isInToggle(new Point(currentX, currentY));
                        if (bean != null) {
                            Point position = positionList.get(bean.getIndex());
                            prePoint = position;
                            path.moveTo(position.x, position.y);
                            IsDownIn = true;
                            invalidate();
                        }
                    }
                }
            }
            break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return true;
    }

    private MarkBean isInToggle(Point point) {
        MarkBean bean = new MarkBean();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            Point position = positionList.get(i);
            ToggleView child = (ToggleView) getChildAt(i);
            if (PositionUtils.IsIn(point, position, child.getOutR())) {
                if (!checkedMap.get(i)) {
                    checkedMap.put(i, true);
                    checkedINdexSet.add(i);
                    bean.setIndex(i);
                    bean.setCheck(true);
                    return bean;
                }
            }
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
        if (prePoint.x != -1 && prePoint.y != -1 && currentPoint.x != -1 && currentPoint.y != -1) {
            canvas.drawLine(prePoint.x, prePoint.y, currentPoint.x, currentPoint.y, paint);
        }
        super.onDraw(canvas);
    }
}
