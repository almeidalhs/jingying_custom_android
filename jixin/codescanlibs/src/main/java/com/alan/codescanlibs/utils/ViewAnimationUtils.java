package com.alan.codescanlibs.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.alan.codescanlibs.view.ScanEyesFindView;
import com.alan.codescanlibs.view.ScanInformationView;
import com.alan.codescanlibs.view.ScanPointView;

import java.util.Random;

/**
 * Created by tangbingliang on 16/12/15.
 */

public class ViewAnimationUtils {

    private viewAnimInterface mViewAnimInterface;
    private boolean isRandomReStart;
    private int childW;
    private int childH;

    public ViewAnimationUtils(viewAnimInterface temp){
        this.mViewAnimInterface = temp;
    }

    public void moveView(final float toX, final float toY, final View temp, long Duration, long StartOffset){
        moveView(toX, toY, temp, Duration, StartOffset, false);
    }

    public void moveView(final float toX, final float toY, final View temp, long Duration
            , long StartOffset, final boolean isRandomReStart){
        this.isRandomReStart = isRandomReStart;
        childH = temp.getHeight()/2;
        childW = temp.getWidth()/2;
        if (temp instanceof ScanEyesFindView) {
            ScanEyesFindView view = (ScanEyesFindView) temp;
            childH = view.getViewHeight()/2;
            childW = view.getViewWidth()/2;
        } else if (temp instanceof ScanPointView) {
            ScanPointView view = (ScanPointView) temp;
            childH = view.getViewWidth()/2;
            childW = view.getViewWidth()/2;
        } else if (temp instanceof ScanInformationView) {
            ScanInformationView view = (ScanInformationView) temp;
            childH = view.getViewHeight()/2;
            childW = view.getViewWidth()/2;
        }

        Log.e("TAG","toX:"+(toX-temp.getLeft()-childW));
        Log.e("TAG","toY:"+(toY-temp.getTop()-childH));
        TranslateAnimation animation = new TranslateAnimation(0, toX-temp.getLeft()-childW
                , 0, toY-temp.getTop()-childH);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(Duration);//设置动画持续时间
        animation.setStartOffset(StartOffset);//设置动画延时时间
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                temp.clearAnimation();
                temp.layout((int) (toX-childW), (int) (toY-childH), (int) (toX+childW), (int) (toY+childH));

                if (temp instanceof ScanPointView) {
                    FrameLayout.LayoutParams mlp =
                            (FrameLayout.LayoutParams) temp.getLayoutParams();
                    mlp.leftMargin = temp.getLeft();
                    mlp.topMargin = temp.getTop();
                    temp.setLayoutParams(mlp);
                }

//                temp.offsetLeftAndRight((int) (toX-temp.getLeft()-childW));
//                temp.offsetTopAndBottom((int) (toY-temp.getTop()-childH));

                TranslateAnimation anim = new TranslateAnimation(0,0,0,0);
                temp.setAnimation(anim);
                if (isRandomReStart && temp instanceof ScanEyesFindView) {
                    randomMoveXY(maxW, maxH, child);
                } else {
                    if (mViewAnimInterface!=null && temp instanceof ScanEyesFindView) {
                        mViewAnimInterface.moveFinsh();
                    }
                }
            }
        });
        temp.startAnimation(animation);
    }

    private int maxW;
    private int maxH;
    private View child;
    public void randomMoveXY(int maxW, int maxH, View child){
        this.maxW = maxW;
        this.maxH = maxH;
        this.child = child;
        ScanEyesFindView view = (ScanEyesFindView) child;
        Random random = new Random();
        int maxX = maxW - view.getViewWidth();
        int maxY = maxH - view.getViewHeight();
        int x = random.nextInt(maxX);
        int y = random.nextInt(maxY);
        x = Math.max(x, view.getViewWidth());
        y = Math.max(y, view.getViewHeight());
        moveView(x, y, child, 300L, 500L, true);
    }

    public void pointMoveXY(int maxW, int maxH, View child){
        ScanPointView view = (ScanPointView) child;
        Random random = new Random();
        int maxX = maxW - view.getViewWidth();
        int maxY = maxH - view.getViewWidth();
        int x = random.nextInt(maxX);
        int y = random.nextInt(maxY);
        x = Math.max(x, view.getViewWidth());
        y = Math.max(y, view.getViewWidth());
        moveView(x, y, child, 500L, 500L, false);
    }

    public void stopMove(int x, int y, View child){
        moveView(x, y, child,0L, 0L);
    }

    public void addInformationView (int x, int y, View child) {
        child.layout(x, y, x+child.getWidth(), y+child.getHeight());
        FrameLayout.LayoutParams mlp =
                (FrameLayout.LayoutParams) child.getLayoutParams();
        mlp.leftMargin = x;
        mlp.topMargin = y;
        child.setLayoutParams(mlp);
    }

    public boolean isRandomReStart() {
        return isRandomReStart;
    }

    public interface viewAnimInterface {
        void moveFinsh();
    }
}
