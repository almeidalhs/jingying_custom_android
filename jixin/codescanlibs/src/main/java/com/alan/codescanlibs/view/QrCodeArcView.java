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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.alan.codescanlibs.R;
import com.alan.codescanlibs.utils.ScreenUtils;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial transparency outside
 * it, as well as the laser scanner animation and result points.
 */
public final class QrCodeArcView extends RelativeLayout {

    private static final long ANIMATION_DELAY = 50L;

    private Context mContext;
    private Paint mPaint;
    private int mTextColor;
    private int mArcColor;
    private Rect mFrameRect;
    private int mDistanceOne;
    private int mLineWidth;
    private int mLineWidthOne;
    private int mTextWidth;
    private int mAngleOne;
    private int mAngleTwo;

    public QrCodeArcView(Context context) {
        this(context, null);
    }

    public QrCodeArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QrCodeArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mDistanceOne = dp2px(context, 5);

        mLineWidth = dp2px(context, 1);
        mLineWidthOne = dp2px(context, 2);
        mTextWidth = 1;

        mAngleOne = 0;
        mAngleTwo = 180;

        Resources resources = getResources();
        mTextColor = resources.getColor(R.color.qr_code_white);
        mArcColor = resources.getColor(R.color.qr_code_flash_light_text_color);
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode()) {
            return;
        }
        // 需要调用下面的方法才会执行onDraw方法
        setWillNotDraw(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.layout_qr_code_scanner, this);
        FrameLayout frameLayout = (FrameLayout) relativeLayout.findViewById(R.id.qr_code_fl_scanner);
        mFrameRect = new Rect();
        LayoutParams layoutParams = (LayoutParams) frameLayout.getLayoutParams();
        mFrameRect.left = (ScreenUtils.getScreenWidth(context) - layoutParams.width) / 2;
        mFrameRect.top = layoutParams.topMargin;
        mFrameRect.right = mFrameRect.left + layoutParams.width;
        mFrameRect.bottom = mFrameRect.top + layoutParams.height;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            return;
        }
        Rect frame = mFrameRect;

        mPaint.setStrokeWidth(mTextWidth);
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);//设置实心

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setStrokeWidth(mLineWidth);
        RectF oval = new RectF(frame.left,frame.top,frame.right,frame.bottom);
        canvas.drawOval(oval, mPaint);

        mPaint.setStrokeWidth(mLineWidthOne);
        mPaint.setColor(mArcColor);
        RectF oval1 = new RectF(frame.left - mDistanceOne,frame.top - mDistanceOne
                ,frame.right + mDistanceOne,frame.bottom + mDistanceOne);
        canvas.drawArc(oval1, mAngleOne, 180,false, mPaint);

        mPaint.setStrokeWidth(mLineWidthOne);
        RectF oval2 = new RectF(frame.left + mDistanceOne,frame.top + mDistanceOne
                ,frame.right - mDistanceOne,frame.bottom - mDistanceOne);
        canvas.drawArc(oval2, mAngleTwo, 180,false, mPaint);

        mAngleOne += 5;
        mAngleTwo -= 5;
        postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
    }

    public int dp2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
