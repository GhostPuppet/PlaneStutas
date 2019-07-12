package com.example.planestutas.myviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.planestutas.R;


public class MyLinearLayoutTwoPart extends LinearLayout {
    private int colorLeft;
    private int colorRight;
    private float heightDifference;
    private float lineAngle;
    private int lineOrientation;
    private Paint paint;

    public MyLinearLayoutTwoPart(Context context) {
        super(context);
    }

    public MyLinearLayoutTwoPart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.MyLinearLayoutTwoPart);
        colorLeft = array.getColor(R.styleable.MyLinearLayoutTwoPart_colorLeft,0xffffff);
        colorRight = array.getColor(R.styleable.MyLinearLayoutTwoPart_colorRight,0xffffff);
        heightDifference = array.getDimension(R.styleable.MyLinearLayoutTwoPart_heightDifference,0);
        lineAngle = array.getFloat(R.styleable.MyLinearLayoutTwoPart_lineAngle,0);
        lineOrientation = array.getInt(R.styleable.MyLinearLayoutTwoPart_lineOrientation,0);
    }

    public MyLinearLayoutTwoPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int width = getWidth();//控件宽度
        int height = getHeight();//控件高度
        if(lineOrientation == 1){
            paint.setColor(colorLeft);
            Path path = new Path();
            //path.moveTo(0,0);
            path.moveTo(0,5);
            path.lineTo(1,2);
            path.lineTo(2,1);
            path.lineTo(5,0);

            path.lineTo(width/2-height/2*lineAngle-4,0);
            path.lineTo(width/2-height/2*lineAngle-1,2);
            path.lineTo(width/2-height/2*lineAngle+1,4);
            path.lineTo(width/2+height/2*lineAngle,height);

            path.lineTo(0,height);
            path.close();
            canvas.drawPath(path,paint);

            path = new Path();
            paint.setColor(colorRight);
            path.moveTo(width/2-((height/2-heightDifference)*lineAngle),heightDifference);
            //path.lineTo(width,heightDifference);
            path.lineTo(width-5,heightDifference);
            path.lineTo(width-3,heightDifference+2);
            path.lineTo(width-2,heightDifference+3);
            path.lineTo(width,heightDifference+5);
            path.lineTo(width,height);

            path.lineTo(width-(width/2-height/2*lineAngle-4),height);
            path.lineTo(width-(width/2-height/2*lineAngle-1),height-2);
            path.lineTo(width-(width/2-height/2*lineAngle+1),height-4);

            path.close();
            canvas.drawPath(path,paint);
        }else if(lineOrientation == -1){
            paint.setColor(colorLeft);
            Path path = new Path();
            //path.moveTo(0,0);
            path.moveTo(width,5);
            path.lineTo(width-1,2);
            path.lineTo(width-2,1);
            path.lineTo(width-5,0);

            path.lineTo(width-(width/2-height/2*lineAngle-4),0);
            path.lineTo(width-(width/2-height/2*lineAngle-1),2);
            path.lineTo(width-(width/2-height/2*lineAngle+1),4);
            path.lineTo(width-(width/2+height/2*lineAngle),height);

            path.lineTo(width,height);
            path.close();
            canvas.drawPath(path,paint);

            path = new Path();
            paint.setColor(colorRight);
            path.moveTo(width-(width/2-((height/2-heightDifference)*lineAngle)),heightDifference);
            //path.lineTo(width,heightDifference);
            path.lineTo(width-(width-5),heightDifference);
            path.lineTo(width-(width-3),heightDifference+2);
            path.lineTo(width-(width-2),heightDifference+3);
            path.lineTo(0,heightDifference+5);
            path.lineTo(0,height);

            path.lineTo(width/2-height/2*lineAngle-4,height);
            path.lineTo(width/2-height/2*lineAngle-1,height-2);
            path.lineTo(width/2-height/2*lineAngle+1,height-4);

            path.close();
            canvas.drawPath(path,paint);
        }

    }
}
