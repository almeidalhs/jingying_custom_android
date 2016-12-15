/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.alan.codescanlibs.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alan.codescanlibs.R;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial transparency outside
 * it, as well as the laser scanner animation and result points.
 */
public final class ScanEyesFindView extends RelativeLayout {

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
    private Bitmap bitmap;
    private int speed = 20;
    private ViewGroup.LayoutParams mLayoutParams;
    private int x=0;
    private int y=0;

    public ScanEyesFindView(Context context) {
        this(context, null);
    }

    public ScanEyesFindView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanEyesFindView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.scan_location_bg_white_light);

        mWidth = dp2px(context, 80);
        mDistanceOne = bitmap.getWidth()*83/522 + dp2px(context, 1);
        mDistanceTwo = bitmap.getWidth()*125/522 - dp2px(context, 1);;
        mLineWidth = dp2px(context, 1);
        mPaint.setStrokeWidth(mLineWidth);

        Resources resources = getResources();
        mArcColorOne = resources.getColor(R.color.qr_code_45d1f1);
        mArcColorTwo = resources.getColor(R.color.qr_code_fec186);

        // 需要调用下面的方法才会执行onDraw方法
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画图片，就是贴图
        canvas.drawBitmap(bitmap, x,y, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setColor(mArcColorOne);
        RectF oval1 = new RectF(mDistanceOne + x
                ,mDistanceOne + y
                ,bitmap.getWidth() - mDistanceOne + x
                ,bitmap.getHeight() - mDistanceOne + y);
        canvas.drawArc(oval1, mAngleOne, 180,false, mPaint);

        mPaint.setColor(mArcColorTwo);
        RectF oval2 = new RectF(mDistanceTwo + x
                ,mDistanceTwo + y
                ,bitmap.getWidth() - mDistanceTwo + x
                ,bitmap.getHeight() - mDistanceTwo + y);
        canvas.drawArc(oval2, mAngleTwo, 180,false, mPaint);

        mAngleOne += speed;
        mAngleTwo -= speed;

        postInvalidateDelayed(ANIMATION_DELAY);
    }

    public int getViewWidth () {
        return bitmap.getWidth();
    }
    public int getViewHeight () {
        return bitmap.getHeight();
    }

    public int dp2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
