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
import com.alan.codescanlibs.utils.ScreenUtils;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial transparency outside
 * it, as well as the laser scanner animation and result points.
 */
public final class ScanInformationView extends RelativeLayout {

    private Context mContext;
    private Paint mPaint;
    private int mDistanceWidth;
    private int mDistanceTX;//文字偏移量
    private int mDistanceY = 0;//y轴偏移量
    private int mLineWidth;
    private int mRightDistance;
    private int mLineColor;
    private Bitmap bitmap;
    private int x=0;
    private int y=0;
    private boolean isLeftImg = true;
    private String name;
    private String str = "请看下面资料!";

    public ScanInformationView(Context context) {
        this(context, null);
    }

    public ScanInformationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanInformationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScanInformationView(Context context, boolean isLeftImg, String name) {
        this(context);
        this.isLeftImg = isLeftImg;
        this.name = name;

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mDistanceWidth = dp2px(context, 30);
        mRightDistance = dp2px(context, 2);
        if (isLeftImg) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.scan_item_board_left);
            x = mDistanceWidth;
            mDistanceTX = 34;
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.scan_item_board_right);
            x = 0;
            mDistanceTX = -17;
        }

        Resources resources = getResources();
        mLineColor = resources.getColor(R.color.qr_code_white);
        mLineWidth = dp2px(context, 1);

        // 需要调用下面的方法才会执行onDraw方法
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap.getWidth()+mDistanceWidth, bitmap.getHeight());
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画图片，就是贴图
        canvas.drawBitmap(bitmap, x,y, mPaint);
        mPaint.setColor(mLineColor);
        mPaint.setTextSize(getResources().getDimension(R.dimen.text_size_13sp));
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;

        if (isLeftImg) {
            canvas.drawLine(0, bitmap.getHeight()/2-mDistanceY, mDistanceWidth, 2, mPaint);
        } else {
            canvas.drawLine(bitmap.getWidth(), 2, bitmap.getWidth()+mDistanceWidth-mRightDistance,
                    bitmap.getHeight()/2-mDistanceY, mPaint);
        }

        float left = (bitmap.getWidth()+mDistanceTX-str.length()*mPaint.getTextSize()) / 2 + x;
        float newY = bitmap.getHeight()/2 + fontTotalHeight;
        canvas.drawText(str, left, newY, mPaint);

        left = (bitmap.getWidth()+mDistanceTX-name.length()*mPaint.getTextSize()) / 2 + x;
        newY = bitmap.getHeight()/2 - fontTotalHeight/2;
        canvas.drawText(name, left, newY, mPaint);
    }

    public boolean isLeftImg() {
        return isLeftImg;
    }

    public int getViewWidth () {
        return bitmap.getWidth();
    }

    public int getmDistanceWidth() {
        return mDistanceWidth;
    }

    public int getViewHeight () {
        return bitmap.getHeight();
    }

    public void setmDistanceY(int mDistanceY) {
        this.mDistanceY = mDistanceY;
        invalidate();
    }

    public int dp2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
