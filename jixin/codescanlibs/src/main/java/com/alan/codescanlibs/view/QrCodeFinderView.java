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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
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
public final class QrCodeFinderView extends RelativeLayout {

    private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192, 128, 64 };
    private static final long ANIMATION_DELAY = 100L;
    private static final int OPAQUE = 0xFF;

    private Context mContext;
    private Paint mPaint;
    private int mScannerAlpha;
    private int mMaskColor;
    private int mTransColor;
    private int mFrameColor;
    private int mLaserColor;
    private int mTextColor;
    private int mArcColor;
    private Rect mFrameRect;
    private int mFocusThick;
    private int mAngleThick;
    private int mAngleLength;
    private int mDistanceOne;
    private int mLineWidth;
    private int mLineWidthOne;
    private int mTextWidth;
    private int mAngleOne;
    private int mAngleTwo;

    public QrCodeFinderView(Context context) {
        this(context, null);
    }

    public QrCodeFinderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QrCodeFinderView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mMaskColor = resources.getColor(R.color.qr_code_finder_mask);
        mTransColor = resources.getColor(R.color.qr_code_finder_trans);
        mFrameColor = resources.getColor(R.color.qr_code_finder_frame);
        mLaserColor = resources.getColor(R.color.qr_code_finder_laser);
        mTextColor = resources.getColor(R.color.qr_code_white);
        mArcColor = resources.getColor(R.color.qr_code_flash_light_text_color);

        mFocusThick = 1;
        mAngleThick = 8;
        mAngleLength = 40;
        mScannerAlpha = 0;
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
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) frameLayout.getLayoutParams();
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

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Rect frame = mFrameRect;

        Bitmap BmpDST = makeBg(frame.width(), frame.height(),mTransColor);
        Canvas c = new Canvas(BmpDST);
        mPaint.setColor(mLaserColor);
        c.drawCircle(frame.width()/2, frame.height()/2, frame.width()/2, mPaint);

        //然后把目标图像画到画布上
        canvas.drawBitmap(BmpDST, frame.left, frame.top,mPaint);

        //计算源图像区域
        Bitmap BmpSRC = makeBg(width, height, mMaskColor);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(BmpSRC,0,0,mPaint);

        mPaint.setXfermode(null);
        mPaint.setStrokeWidth(mTextWidth);
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);//设置实心
        drawText(canvas, frame);//绘制文字
        drawTextTwo(canvas, frame);//绘制文字

//        mPaint.setColor(mTextColor);
//        mPaint.setStyle(Paint.Style.STROKE);//设置空心
//        mPaint.setStrokeWidth(mLineWidth);
//        RectF oval = new RectF(frame.left,frame.top,frame.right,frame.bottom);
//        canvas.drawOval(oval, mPaint);
//
//        mPaint.setStrokeWidth(mLineWidthOne);
//        mPaint.setColor(mArcColor);
//        RectF oval1 = new RectF(frame.left - mDistanceOne,frame.top - mDistanceOne
//                ,frame.right + mDistanceOne,frame.bottom + mDistanceOne);
//        canvas.drawArc(oval1, mAngleOne, 180,false, mPaint);
//
//        mPaint.setStrokeWidth(mLineWidthOne);
//        RectF oval2 = new RectF(frame.left + mDistanceOne,frame.top + mDistanceOne
//                ,frame.right - mDistanceOne,frame.bottom - mDistanceOne);
//        canvas.drawArc(oval2, mAngleTwo, 180,false, mPaint);
//
//        mAngleOne += 5;
//        mAngleTwo -= 5;
//        postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);


//        Rect frame = mFrameRect;
//        if (frame == null) {
//            return;
//        }
//        int width = canvas.getWidth();
//        int height = canvas.getHeight();
//
//        // 绘制焦点框外边的暗色背景
//        mPaint.setColor(mMaskColor);
//        canvas.drawRect(0, 0, width, frame.top, mPaint);//上面区域
//        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, mPaint);//左边区域
//        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, mPaint);//右边区域
//        canvas.drawRect(0, frame.bottom + 1, width, height, mPaint);//下边区域
//
//        drawFocusRect(canvas, frame);
//        drawAngle(canvas, frame);
//        drawText(canvas, frame);
//        drawLaser(canvas, frame);
//
//        // Request another update at the animation interval, but only repaint the laser line,
//        // not the entire viewfinder mask.
//        postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
    }

    // create a bitmap with a circle, used for the "dst" image
    private Bitmap makeOval(Rect frame) {
        Bitmap bm = Bitmap.createBitmap(frame.width(), frame.height(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(mLaserColor);
//        c.drawCircle(frame.left, frame.top, frame.width()/2, p);
        c.drawOval(new RectF(frame.left, frame.top, frame.right, frame.bottom), p);
        return bm;
    }

        // create a bitmap with a rect, used for the "src" image
    private Bitmap makeBg(int w, int h, int mColor) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(mColor);
        c.drawRect(0, 0, w, h, p);
        return bm;
    }

    /**
     * 画聚焦框，白色的
     *
     * @param canvas
     * @param rect
     */
    private void drawFocusRect(Canvas canvas, Rect rect) {
        // 绘制焦点框（黑色）
        mPaint.setColor(mFrameColor);
        // 上
        canvas.drawRect(rect.left + mAngleLength, rect.top, rect.right - mAngleLength, rect.top + mFocusThick, mPaint);
        // 左
        canvas.drawRect(rect.left, rect.top + mAngleLength, rect.left + mFocusThick, rect.bottom - mAngleLength,
                mPaint);
        // 右
        canvas.drawRect(rect.right - mFocusThick, rect.top + mAngleLength, rect.right, rect.bottom - mAngleLength,
                mPaint);
        // 下
        canvas.drawRect(rect.left + mAngleLength, rect.bottom - mFocusThick, rect.right - mAngleLength, rect.bottom,
                mPaint);
    }

    /**
     * 画粉色的四个角
     *
     * @param canvas
     * @param rect
     */
    private void drawAngle(Canvas canvas, Rect rect) {
        mPaint.setColor(mLaserColor);
        mPaint.setAlpha(OPAQUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mAngleThick);
        int left = rect.left;
        int top = rect.top;
        int right = rect.right;
        int bottom = rect.bottom;
        // 左上角
        canvas.drawRect(left, top, left + mAngleLength, top + mAngleThick, mPaint);
        canvas.drawRect(left, top, left + mAngleThick, top + mAngleLength, mPaint);
        // 右上角
        canvas.drawRect(right - mAngleLength, top, right, top + mAngleThick, mPaint);
        canvas.drawRect(right - mAngleThick, top, right, top + mAngleLength, mPaint);
        // 左下角
        canvas.drawRect(left, bottom - mAngleLength, left + mAngleThick, bottom, mPaint);
        canvas.drawRect(left, bottom - mAngleThick, left + mAngleLength, bottom, mPaint);
        // 右下角
        canvas.drawRect(right - mAngleLength, bottom - mAngleThick, right, bottom, mPaint);
        canvas.drawRect(right - mAngleThick, bottom - mAngleLength, right, bottom, mPaint);
    }

    private void drawText(Canvas canvas, Rect rect) {
        int margin = 40;
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(getResources().getDimension(R.dimen.text_size_13sp));
        String text = getResources().getString(R.string.qr_code_auto_scan_notification);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offY = fontTotalHeight / 2 - fontMetrics.bottom;
        float newY = rect.bottom + margin + offY + mDistanceOne;
        float left = (ScreenUtils.getScreenWidth(mContext) - mPaint.getTextSize() * text.length()) / 2;
        canvas.drawText(text, left, newY, mPaint);
    }

    private void drawTextTwo(Canvas canvas, Rect rect) {
        int margin = 40;
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(getResources().getDimension(R.dimen.text_size_13sp));
        String text = getResources().getString(R.string.qr_code_auto_scan_notification_two);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offY = fontTotalHeight / 2 - fontMetrics.bottom;
        float newY = rect.bottom + margin + offY + mDistanceOne + getResources().getDimension(R.dimen.text_size_15sp);
        float left = (ScreenUtils.getScreenWidth(mContext) - mPaint.getTextSize() * text.length()) / 2;
        canvas.drawText(text, left, newY, mPaint);
    }

    private void drawLaser(Canvas canvas, Rect rect) {
        // 绘制焦点框内固定的一条扫描线（红色）
        mPaint.setColor(mLaserColor);
        mPaint.setAlpha(SCANNER_ALPHA[mScannerAlpha]);
        mScannerAlpha = (mScannerAlpha + 1) % SCANNER_ALPHA.length;
        int middle = rect.height() / 2 + rect.top;
        canvas.drawRect(rect.left + 2, middle - 1, rect.right - 1, middle + 2, mPaint);

    }

    public int dp2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
