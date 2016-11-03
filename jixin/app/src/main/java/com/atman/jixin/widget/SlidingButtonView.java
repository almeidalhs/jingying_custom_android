package com.atman.jixin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.atman.jixin.R;

public class SlidingButtonView extends HorizontalScrollView   {

    private TextView mTextView_Delete;
    private TextView mTextView_Member;

    private int mScrollWidth;

    private IonSlidingButtonListener mIonSlidingButtonListener;

    private Boolean isOpen = false;
    private Boolean once = false;
    private Context context;


    public SlidingButtonView(Context context) {
        this(context, null);
    }

    public SlidingButtonView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(!once){
            mTextView_Delete = (TextView) findViewById(R.id.tv_delete);
            mTextView_Member = (TextView) findViewById(R.id.tv_member);
            once = true;
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(0,0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            if (mTextView_Member!=null) {
                mScrollWidth = mTextView_Delete.getWidth() + mTextView_Member.getWidth();
            } else {
                mScrollWidth = mTextView_Delete.getWidth();
            }
        }

    }

    public void changeWidth(int num, int width){
        mScrollWidth = dip2px(width * num);
//        if (num==1) {
//            mScrollWidth = dip2px(80);
//        } else if (num==2) {
//            mScrollWidth = dip2px(160);
//        } else if (num==3) {
//            mScrollWidth = dip2px(240);
//        } else {
//            mScrollWidth = dip2px(0);
//        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mIonSlidingButtonListener.onDownOrMove(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollx();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        mTextView_Delete.setTranslationX(l - mScrollWidth);
    }

    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollx(){
        if(getScrollX() >= (mScrollWidth/2)){
            this.smoothScrollTo(mScrollWidth, 0);
            isOpen = true;
            mIonSlidingButtonListener.onMenuIsOpen(this);
        }else{
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen){
            return;
        }
        this.smoothScrollTo(mScrollWidth, 0);
        isOpen = true;
        mIonSlidingButtonListener.onMenuIsOpen(this);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen){
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }

    public void setSlidingButtonListener(IonSlidingButtonListener listener){
        mIonSlidingButtonListener = listener;
    }

    public interface IonSlidingButtonListener{
        void onMenuIsOpen(View view);
        void onDownOrMove(SlidingButtonView slidingButtonView);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
