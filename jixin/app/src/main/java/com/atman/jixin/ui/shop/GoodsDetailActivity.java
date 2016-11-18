package com.atman.jixin.ui.shop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.bean.LocationNumberModel;
import com.atman.jixin.model.greendao.gen.LocationNumberModelDao;
import com.atman.jixin.model.response.GoodsDetailModel;
import com.atman.jixin.model.response.IntegralGoodsModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.personal.RegisterActivity;
import com.atman.jixin.utils.Common;
import com.atman.jixin.widget.ExchangeEditTextDialog;
import com.base.baselibs.net.BaseNormalModel;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.PromptDialog;
import com.base.baselibs.widget.adview.ADInfo;
import com.base.baselibs.widget.adview.CycleViewPager;
import com.base.baselibs.widget.adview.ViewFactory;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class GoodsDetailActivity extends MyBaseActivity implements ExchangeEditTextDialog.ETOnClick {

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
    @Bind(R.id.goodsdetail_bottom_line)
    ImageView goodsdetailBottomLine;
    @Bind(R.id.goodsdetail_bottom_ll)
    LinearLayout goodsdetailBottomLl;

    private Context mContext = GoodsDetailActivity.this;
    private long goodsId;
    private String title;
    private int formId;
    private int myIntegral;
    private IntegralGoodsModel.BodyBean mBodyBean;
    private GoodsDetailModel mGoodsDetailModel;
    private int displayNum;
    private int cion;
    private long id;
    private int totalNum = 0;
    private ExchangeEditTextDialog dialog;

    private LocationNumberModelDao mLocationNumberModelDao;

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

    public static Intent buildIntent(Context context, long goodsId, String title, int formId
            , int myIntegral, IntegralGoodsModel.BodyBean mBodyBean) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("title", title);
        intent.putExtra("formId", formId);
        intent.putExtra("myIntegral", myIntegral);
        Bundle b = new Bundle();
        b.putSerializable("body", mBodyBean);
        intent.putExtras(b);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        goodsId = getIntent().getLongExtra("goodsId", -1);
        formId = getIntent().getIntExtra("formId", 0);
        title = getIntent().getStringExtra("title");

        setBarTitleTx(title);

        getBarBackLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(), getmWidth());
        mallTopAdLl.setLayoutParams(params);

        if (formId == 1) {
            goodsdetailBottomLine.setVisibility(View.VISIBLE);
            goodsdetailBottomLl.setVisibility(View.VISIBLE);

            mBodyBean = (IntegralGoodsModel.BodyBean) getIntent().getSerializableExtra("body");
            myIntegral = getIntent().getIntExtra("myIntegral", 0);
            mLocationNumberModelDao = MyBaseApplication.getApplication().getDaoSession().getLocationNumberModelDao();
        }
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
        } else if (id == Common.NET_EXCHANGE_ID) {
            BaseNormalModel temp = mGson.fromJson(data, BaseNormalModel.class);
            if (temp.getResult().equals("1")) {
                if (dialog!=null) {
                    dialog.dismiss();
                }
                showToast("商品兑换成功");
                myIntegral -= (cion*totalNum);
                if (myIntegral<0) {
                    myIntegral = 0;
                }
            }
        }
    }

    private void updateView() {
        goodsdetailNameTv.setText(mGoodsDetailModel.getBody().getGoodsName());
        goodsdetailPriceTv.setText("￥ " + mGoodsDetailModel.getBody().getPrice());
        if (mGoodsDetailModel.getBody().getGoodsSpec() == null
                || mGoodsDetailModel.getBody().getGoodsSpec().isEmpty()) {
            goodsdetailIntroductionTv.setText("暂未添加规格");
        } else {
            goodsdetailIntroductionTv.setText(mGoodsDetailModel.getBody().getGoodsSpec());
        }
        if (mGoodsDetailModel.getBody().getGoodsDescription() == null
                || mGoodsDetailModel.getBody().getGoodsDescription().isEmpty()) {
            goodsdetailDescriptionTv.setText("暂未添加说明");
        } else {
            goodsdetailDescriptionTv.setText(mGoodsDetailModel.getBody().getGoodsDescription());
        }

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
        if (infos.size() <= 1) {
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
            if (position < 0) {
                position = 0;
            }

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GOODSDETAI_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_EXCHANGE_ID);
    }

    @OnClick({R.id.goodsdetail_bottom_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goodsdetail_bottom_ll:
                if (isVisitors()) {
                    showToBindPhone();
                    return;
                }
                if (mBodyBean.getIntegral() > myIntegral) {
                    showWraning("对不起,您的积分不够兑换此商品!");
                } else {
                    String str = "本次可兑换数量:1";
                    if (mBodyBean.getStoreLimit() >= 1) {
                        if (mBodyBean.getStoreLimit() >= mBodyBean.getUserLimit()) {
                            if (mBodyBean.getUserLimit() > 1) {
                                str += "~" + mBodyBean.getUserLimit();
                                displayNum = mBodyBean.getUserLimit();
                            }
                        } else {
                            str += "~" + mBodyBean.getStoreLimit();
                            displayNum = mBodyBean.getStoreLimit();
                        }
                        cion = mBodyBean.getIntegral();
                        id = mBodyBean.getId();
                        //获取桌号
                        LocationNumberModel locationNumberModel = mLocationNumberModelDao.queryBuilder()
                                .where(LocationNumberModelDao.Properties.TargetId.eq(mBodyBean.getStoreId())
                                        , LocationNumberModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();

                        String content = "";
                        if (locationNumberModel != null && locationNumberModel.getLocation() != null) {
                            content = locationNumberModel.getLocation();
                        }

                        dialog = new ExchangeEditTextDialog(mContext, str, content, this);
                        dialog.show();
                    } else {
                        showWraning("此商品已兑换完,看看其他商品吧");
                    }
                }
                break;
        }
    }

    private void showToBindPhone() {
        PromptDialog.Builder builder = new PromptDialog.Builder(GoodsDetailActivity.this);
        builder.setMessage("对不起,您需要绑定手机注册为正式帐号之后才能兑换商品!");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("去绑定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });
        builder.show();
    }

    @Override
    public void onItemClick(View view, String str1, String str2) {
        switch (view.getId()) {
            case R.id.exchangeedittext_dialog_cancel_tx:
                dialog.dismiss();
                break;
            case R.id.exchangeedittext_dialog_ok_tx:
                if (str1.isEmpty()) {
                    showToast("请输入兑换数量");
                    return;
                }
                if (Integer.parseInt(str1) > displayNum) {
                    showToast("超过最大兑换数量");
                    return;
                }
                dialog.dismiss();
                showAgain(cion, Integer.parseInt(str1), str2);
                break;
        }
    }

    private void showAgain(int cion, final int num, final String location) {
        totalNum = num;
        PromptDialog.Builder builder = new PromptDialog.Builder(GoodsDetailActivity.this);
        builder.setMessage("你确定要花"+(cion*num)+"积分\n兑换该商品x"+num);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Map<String, String> p = new HashMap<String, String>();
                p.put("id", id+"");
                p.put("amount", totalNum+"");
                p.put("addressDetail", location);
                OkHttpUtils.postString().url(Common.Url_Exchange).tag(Common.NET_EXCHANGE_ID)
                        .id(Common.NET_EXCHANGE_ID).content(mGson.toJson(p)).mediaType(Common.JSON)
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .build().execute(new MyStringCallback(mContext, "兑换中..."
                        , GoodsDetailActivity.this, true));
            }
        });
        builder.show();
    }

    @Override
    public void onTouchOutside(EditText edittextDialogEt) {
        if (isIMOpen() && edittextDialogEt.getWindowToken()!=null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edittextDialogEt.getWindowToken(), 0); //强制隐藏键盘
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void back(){
        LogUtils.e(">>>>>>>myIntegral:"+myIntegral);
        Intent mIntent = new Intent();
        setResult(RESULT_OK,mIntent);
        mIntent.putExtra("myIntegral", myIntegral);
        finish();
    }
}
