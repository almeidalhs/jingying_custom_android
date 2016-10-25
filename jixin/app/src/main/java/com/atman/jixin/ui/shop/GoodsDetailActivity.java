package com.atman.jixin.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.GoodsDetailModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.adview.ADInfo;
import com.base.baselibs.widget.adview.CycleViewPager;
import com.base.baselibs.widget.adview.ViewFactory;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class GoodsDetailActivity extends MyBaseActivity {

    @Bind(R.id.mall_top_ad_ll)
    LinearLayout mallTopAdLl;
    @Bind(R.id.goodsdetail_name_tv)
    TextView goodsdetailNameTv;
    @Bind(R.id.goodsdetail_price_tv)
    TextView goodsdetailPriceTv;
    @Bind(R.id.goodsdetail_introduction_tv)
    TextView goodsdetailIntroductionTv;
    @Bind(R.id.goodsdetail_description_tv)
    TextView goodsdetailDescriptionTv;

    private Context mContext = GoodsDetailActivity.this;
    private long goodsId;
    private String title;
    private GoodsDetailModel mGoodsDetailModel;

    private CycleViewPager cycleViewPager;
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private String[] imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetail);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long goodsId, String title) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("title", title);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        goodsId = getIntent().getLongExtra("goodsId", -1);
        title = getIntent().getStringExtra("title");

        setBarTitleTx(title);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(), getmWidth());
        mallTopAdLl.setLayoutParams(params);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();

        OkHttpUtils.get().url(Common.Url_Get_GoodsDetail + goodsId)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_GOODSDETAI_ID).id(Common.NET_GET_GOODSDETAI_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_GOODSDETAI_ID) {
            mGoodsDetailModel = mGson.fromJson(data, GoodsDetailModel.class);

            updateView();
        }
    }

    private void updateView() {
        goodsdetailNameTv.setText(mGoodsDetailModel.getBody().getGoodsName());
        goodsdetailPriceTv.setText("￥ "+mGoodsDetailModel.getBody().getPrice());
        goodsdetailIntroductionTv.setText(mGoodsDetailModel.getBody().getGoodsSpec());
        goodsdetailDescriptionTv.setText(mGoodsDetailModel.getBody().getGoodsDescription());

        imageUrls = new String[mGoodsDetailModel.getBody().getGoodsImageMoreList().size()];
        for (int i = 0; i < mGoodsDetailModel.getBody().getGoodsImageMoreList().size(); i++) {
            imageUrls[i] = mGoodsDetailModel.getBody().getGoodsImageMoreList().get(i);
        }
        initialize();
    }

    private void initialize() {

        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

        infos.clear();
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        views.clear();
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(mContext, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            ImageView img = ViewFactory.getImageView(mContext, infos.get(i).getUrl());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(img);
        }
        // 将第一个ImageView添加进来
        ImageView imageView = ViewFactory.getImageView(mContext, infos.get(0).getUrl());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        views.add(imageView);

        // 设置循环，在调用setData方法前调用
        if (infos.size()<=1) {
            cycleViewPager.setCycle(false);
            cycleViewPager.setScrollable(false);
            cycleViewPager.setWheel(false);
        } else {
            cycleViewPager.setCycle(true);
            cycleViewPager.setScrollable(true);
            cycleViewPager.setWheel(false);
//            cycleViewPager.setTime(5000);
        }
        cycleViewPager.setData(views, infos, mAdCycleViewListener);

        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorRight();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
            }
            if (position<0) {
                position = 0;
            }

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GOODSDETAI_ID);
    }
}
