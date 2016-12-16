package com.alan.codescanlibs.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alan.codescanlibs.R;

/**
 * Created by tangbingliang on 16/12/14.
 */

public class ScanPointView extends RelativeLayout {

    private static final long ANIMATION_DELAY = 50L;

    private Context mContext;
    private Paint mPaint;
    private int mWidth;
    private int mArcColorOne;
    private int mArcColorTwo;
    private int mDistanceOne;
    private int mDistanceTwo;
    private int mLineWidth;
    private int mAngleOne;
    private int mAngleTwo;
    private int speed = 20;
    private ViewGroup.LayoutParams mLayoutParams;
    private int x=0;
    private int y=0;
    private int radius;

    public ScanPointView(Context context) {
        this(context, null);
    }

    public ScanPointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mWidth = dp2px(context, 18);
        mDistanceOne = dp2px(context, 1);
        mDistanceTwo = dp2px(context, 4);
        mLineWidth = dp2px(context, 1);
        radius = dp2px(context, 2);

        Resources resources = getResources();
        mArcColorOne = resources.getColor(R.color.qr_code_399269);
        mArcColorTwo = resources.getColor(R.color.qr_code_d1dbcd);

        // 需要调用下面的方法才会执行onDraw方法
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mArcColorOne);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth/2,mWidth/2, radius, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setStrokeWidth(mLineWidth);
        RectF oval1 = new RectF(mDistanceOne + x
                ,mDistanceOne + y
                ,mWidth - mDistanceOne + x
                ,mWidth - mDistanceOne + y);
        canvas.drawArc(oval1, mAngleOne, 230,false, mPaint);

        mPaint.setColor(mArcColorTwo);
        mPaint.setStrokeWidth(mLineWidth);
        RectF oval2 = new RectF(mDistanceTwo + x
                ,mDistanceTwo + y
                ,mWidth - mDistanceTwo + x
                ,mWidth - mDistanceTwo + y);
        canvas.drawArc(oval2, mAngleTwo, 230,false, mPaint);

        mAngleOne -= speed;
        mAngleTwo += speed;

        postInvalidateDelayed(ANIMATION_DELAY);
    }

    public int getViewWidth () {
        return mWidth;
    }

    public int dp2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
