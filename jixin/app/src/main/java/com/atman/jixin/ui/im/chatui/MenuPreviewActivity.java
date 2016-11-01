package com.atman.jixin.ui.im.chatui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.atman.jixin.R;
import com.atman.jixin.adapter.TabAdapter;
import com.atman.jixin.model.response.GoodsClassModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.NoSwipeViewPager;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/31.
 */

public class MenuPreviewActivity extends MyBaseActivity {

    @Bind(R.id.menupreview_top_tl)
    TabLayout menupreviewTopTl;
    @Bind(R.id.menupreview_viewpager)
    NoSwipeViewPager menupreviewViewpager;

    private Context mContext = MenuPreviewActivity.this;
    private String title;
    private long storeId;

    private GoodsClassModel mGoodsClassModel;
    private List<GoodsClassModel.BodyBean> temp = new ArrayList<>();
    private List<Fragment> fragments;
    private TabAdapter fragPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupreview);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String title, long storeId) {
        Intent intent = new Intent(context, MenuPreviewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("storeId", storeId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        title = getIntent().getStringExtra("title");
        storeId = getIntent().getLongExtra("storeId", -1);

        setBarTitleTx(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Store_GoodsClass + storeId)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_STORE_GOODSCLASS_ID).id(Common.NET_GET_STORE_GOODSCLASS_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_STORE_GOODSCLASS_ID) {
            mGoodsClassModel = mGson.fromJson(data, GoodsClassModel.class);
            GoodsClassModel.BodyBean allItem = new GoodsClassModel.BodyBean();
            allItem.setId(0);
            allItem.setStoreId(storeId);
            allItem.setStcName("全部");

            for (int i = 0; i < mGoodsClassModel.getBody().size(); i++) {
                if (i==0) {
                    temp.add(allItem);
                }
                if (mGoodsClassModel.getBody().get(i).getGoodsShowCount()>0) {
                    temp.add(mGoodsClassModel.getBody().get(i));
                }
            }

            initTopGoodsClass();
        }
    }

    private void initTopGoodsClass() {
        fragments = new ArrayList<>();
        for (int i=0;i<temp.size();i++) {
            MenuPreviewFragment oneFragment = new MenuPreviewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TITLES", temp.get(i).getStcName());
            bundle.putString("typeId", String.valueOf(temp.get(i).getId()));
            bundle.putString("storeId", String.valueOf(temp.get(i).getStoreId()));
            oneFragment.setArguments(bundle);
            fragments.add(oneFragment);
        }
        fragPagerAdapter = new TabAdapter(getSupportFragmentManager());
        //设置显示的标题
        fragPagerAdapter.setList(temp);
        //设置需要进行滑动的页面Fragment
        fragPagerAdapter.setFragments(fragments);
        menupreviewViewpager.setOffscreenPageLimit(temp.size());
        menupreviewViewpager.setAdapter(fragPagerAdapter);
        menupreviewTopTl.setupWithViewPager(menupreviewViewpager);
        //设置TabLayout模式 -该使用Tab数量比较多的情况
        menupreviewTopTl.setTabMode(menupreviewTopTl.MODE_SCROLLABLE);
        menupreviewViewpager.setPagingEnabled(true);//是否支持手势滑动
        menupreviewTopTl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                menupreviewViewpager.setCurrentItem(menupreviewTopTl.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_STORE_GOODSCLASS_ID);
    }
}
