package com.base.baselibs.widget.WheelView;

import android.content.Context;
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.base.baselibs.R;
import com.base.baselibs.adapter.PopwindowsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/22 14:08
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BirthdayPopwindow {

    private Context context;
    private WheelView mYearWheel, mMonthWheel, mDayWheel;
    private PopwindowsAdapter mYearAdapter, mMonthAdapter, mDayAdapter;
    private String mYear, mMonth, mDay;
    private int mYearId = 0;
    private int mMonthId = 0;
    private int mDayId = 0;
    private List<String> mYearList = new ArrayList<>();
    private List<String> mMonthList = new ArrayList<>();
    private List<String> mDayList = new ArrayList<>();
    private PopWindowsCallback mPopWindowsCallback;
    private int[] num = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private String date;
    private TextView popNewTx;
    private Time time;

    public BirthdayPopwindow(Context context, String date, PopWindowsCallback mPopWindowsCallback) {
        this.context = context;
        this.mPopWindowsCallback = mPopWindowsCallback;
        this.date = date;
        time = new Time("GMT+8");
        time.setToNow();
        initData();
    }

    private void initData() {
        String[] str = date.split("-");
        mYear = str[0];
        mMonth = str[1];
        mDay = str[2];
        for (int i=1930,n=0;i<2026;i++,n++) {
            mYearList.add(i+"");
            if (mYear.equals(i+"")) {
                mYearId = n;
            }
        }
        for (int i=1,n=0;i<=12;i++,n++) {
            mMonthList.add(i+"");
            if (Integer.parseInt(mMonth)==i) {
                mMonthId = n;
            }
        }
        for (int i=1,n=0;i<=num[0];i++,n++) {
            mDayList.add(i+"");
            if (Integer.parseInt(mDay)==i) {
                mDayId = n;
            }
        }
    }

    private int getNowYear() {
        int y = 0;
        for (int i=1930,n=0;i<2026;i++,n++) {
            if (time.year == i) {
                y = n;
                mYear = i+"";
            }
        }
        return y;
    }

    private int getNowMonth() {
        int y = 0;
        for (int i=1,n=0;i<=12;i++,n++) {
            if ((time.month+1) == i) {
                y = n;
                mMonth = i+"";
            }
        }
        return y;
    }

    private int getNowMonthDay() {
        int y = 0;
        for (int i=1,n=0;i<=num[0];i++,n++) {
            if (time.monthDay==i) {
                y = n;
                mDay = i+"";
            }
        }
        return y;
    }

    public void showTypePopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        final View contentView = LayoutInflater.from(context).inflate(R.layout.wheel_birthday_view, null);

        final PopupWindow mSelectTypePop = new PopupWindow(contentView,
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT, true);

        popNewTx = (TextView) contentView.findViewById(R.id.pop_new_tx);
        popNewTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYearWheel.setCurrentItem(getNowYear(), true);
                mMonthWheel.setCurrentItem(getNowMonth(), true);
                mDayWheel.setCurrentItem(getNowMonthDay(), true);
            }
        });

        mYearWheel = (WheelView) contentView.findViewById(R.id.popwin_select_year);
        mYearAdapter = new PopwindowsAdapter(context, mYearWheel, mYearList, 0, 0, 0);
        mYearWheel.setVisibleItems(5);
        mYearWheel.setViewAdapter(mYearAdapter);
        mYearWheel.setCurrentItem(mYearId);
        mYearWheel.setCyclic(true);
        mYearWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mYearWheel.setViewAdapter(mYearAdapter);
            }
        });
        mYearWheel.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                mYear = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                mYearId = wheel.getCurrentItem();
                if (Integer.parseInt(mYear) >= time.year) {
                    mYearWheel.setCurrentItem(getNowYear(), true);
                    mMonthWheel.setCurrentItem(getNowMonth(), true);
                    mDayWheel.setCurrentItem(getNowMonthDay(), true);
                }
            }
        });

        mMonthWheel = (WheelView) contentView.findViewById(R.id.popwin_select_month);
        mMonthAdapter = new PopwindowsAdapter(context, mMonthWheel, mMonthList, 0, 0, 0);
        mMonthWheel.setVisibleItems(5);
        mMonthWheel.setViewAdapter(mMonthAdapter);
        mMonthWheel.setCurrentItem(mMonthId);
        mMonthWheel.setCyclic(true);
        mMonthWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mMonthWheel.setViewAdapter(mMonthAdapter);
            }
        });
        mMonthWheel.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                mMonth = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                mMonthId = wheel.getCurrentItem();

                mDayList.clear();
                for (int i=1;i<=num[mMonthId];i++) {
                    mDayList.add(i+"");
                }
                mDayAdapter = new PopwindowsAdapter(context, mDayWheel, mDayList, 0, 0, 0);
                mDayWheel.setViewAdapter(mDayAdapter);
                if (mDayId>=mDayAdapter.getItemsCount()) {
                    mDayWheel.setCurrentItem(mDayAdapter.getItemsCount()-1, true);
                } else {
                    mDayWheel.setCurrentItem(mDayId, true);
                }

                if (Integer.parseInt(mYear) >= time.year
                        && Integer.parseInt(mMonth) >= (time.month+1)) {
                    if (Integer.parseInt(mMonth) > (time.month+1)) {
                        mMonthWheel.setCurrentItem(getNowMonth(), true);
                    }
                    mDayWheel.setCurrentItem(getNowMonthDay(), true);
                    mDayId = getNowMonthDay();
                }
            }
        });

        mDayWheel = (WheelView) contentView.findViewById(R.id.popwin_select_day);
        mDayAdapter = new PopwindowsAdapter(context, mDayWheel, mDayList, 0, 0, 0);
        mDayWheel.setVisibleItems(5);
        mDayWheel.setViewAdapter(mDayAdapter);
        mDayWheel.setCurrentItem(mDayId);
        mDayWheel.setCyclic(true);
        mDayWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mDayWheel.setViewAdapter(mDayAdapter);
            }
        });
        mDayWheel.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                mDay = (String) mDayAdapter.getItemText(wheel.getCurrentItem());
                mDayId = wheel.getCurrentItem();
                if (Integer.parseInt(mYear) >= time.year
                        && Integer.parseInt(mMonth) >= (time.month+1)
                        && Integer.parseInt(mDay) > time.monthDay) {
                    mDayWheel.setCurrentItem(getNowMonthDay(), true);
                }
            }
        });


        Button pop_cancel = (Button) contentView.findViewById(R.id.pop_cancel);
        Button pop_ok = (Button) contentView.findViewById(R.id.pop_ok);
        pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectTypePop != null) {
                    mSelectTypePop.dismiss();
                }
            }
        });
        pop_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectTypePop != null) {
                    mSelectTypePop.dismiss();
                }
                mPopWindowsCallback.selectItem(mYear+"-"+mMonth+"-"+mDay);
            }
        });

        mSelectTypePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        mSelectTypePop.setTouchable(true);
        mSelectTypePop.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        mSelectTypePop.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pop_bg));
        // 设置好参数之后再show
        mSelectTypePop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public interface PopWindowsCallback {
        void selectItem(String str);
    }
}
