package com.first.hdz.ninelock;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hdz
 * on 2018/8/7
 */
public class PositionUtils {
    /**
     * 判断触摸的点是否属于View中的一点
     *
     * @param point    触摸的点
     * @param position 目标对象圆形坐标
     * @param outR     目标对象的外半径
     * @return
     */
    public static boolean IsIn(Point point, Point position, int outR) {
        int touchX = point.x;
        int touchY = point.y;

        int cx = position.x;
        int cy = position.y;

        int distance = (int) Math.sqrt(Math.pow((touchX - cx), 2) + Math.pow((touchY - cy), 2));
        if (distance <= outR) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 规划 child 的中心位置
     *
     * @param width
     * @param height
     * @return
     */
    public static List<Point> getNinePoints(int width, int height) {
        List<Point> points = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                points.add(getPoint(width, height, 0.25f * j, 0.2f * i + 0.1f));
            }
        }
        return points;
    }

    /**
     * 获取
     *
     * @param width  父控件的宽
     * @param height 父控件的高
     * @param x      横轴方向比例
     * @param y      纵轴方向的比例
     * @return
     */
    private static Point getPoint(int width, int height, float x, float y) {
        Point point = new Point();
        point.x = (int) (width * x);
        point.y = (int) (height * y);
        return point;
    }

}
