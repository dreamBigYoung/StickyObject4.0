package com.example.bigyoung.stickyobject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.bigyoung.stickyobject.utils.GeometryUtil;
import com.example.bigyoung.stickyobject.utils.Utils;

/**
 * Created by BigYoung on 2017/3/26.
 */

public class StickyObject extends View {
    private static final int LARGEST_DISTANCE =100;
    private PointF consPoint;//静态圆圆心
    private PointF variPoint;//动员圆心

    private final float mConsRadius=50.0f;//静态圆的半径
    private final float mVariRadius=70.0f;//动态圆的半径

    private int topDistance;//this 相对于parent view的距离

    Paint paintCir;//绘制circle用的画笔
    private boolean isExist;

    public StickyObject(Context context) {
        this(context, null);
    }

    public StickyObject(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyObject(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //init
        consPoint=new PointF(100.0f,100.0f);
        variPoint=new PointF(100.0f,100.0f);
        //init
        paintCir=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCir.setColor(Color.BLACK);
        paintCir.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制静态圆
        //设置canvas,寄存之前的绘制状态，对之后的绘制无影响
        canvas.save();
        canvas.translate(0,-topDistance);
        //绘制静态圆
        canvas.drawCircle(consPoint.x,consPoint.y,mConsRadius,paintCir);
        //绘制动态圆
        canvas.drawCircle(variPoint.x,variPoint.y,mVariRadius,paintCir);

        //取出之前的绘制状态
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isDrawVariCircle(event.getRawX(),event.getRawY());
                if(isExist==true)
                    updateVariPoint(event.getRawX(),event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                fixDraw(event.getRawX(),event.getRawY());
                break;
        }
        return true;
    }

    /**
     * 判断该点击事件是否可以进行绘制
     * @param rawX
     * @param rawY
     */
    private void isDrawVariCircle(float rawX, float rawY) {
        //获得当前圆心距
        PointF old=new PointF(rawX,rawY);
        float current= GeometryUtil.getDistanceBetween2Points(old,consPoint);
        if(current<=LARGEST_DISTANCE){
            isExist=true;//设置为可以显示
        }else{
            isExist=false;
        }
    }

    /**
     * 根据动点的坐标判断绘制图片的状态
     * @param rawX
     * @param rawY
     */
    private void fixDraw(float rawX, float rawY) {
        //LARGEST_DISTANCE
        //获得当前圆心距
        PointF old=new PointF(rawX,rawY);
        float current= GeometryUtil.getDistanceBetween2Points(old,consPoint);
        //判断点间距
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    /**
     * 重置动态圆圆心坐标
     * @param rawX
     * @param rawY
     */
    private void updateVariPoint(float rawX, float rawY) {
        variPoint.set(rawX,rawY);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        topDistance=top+Utils.getViewTopLocation(StickyObject.this);// Utils.getViewTopLocation(this);
    }
}
