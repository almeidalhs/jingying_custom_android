package com.atman.jixin.ui.shop;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.IntegralExchangeAdapter;
import com.atman.jixin.model.bean.LocationNumberModel;
import com.atman.jixin.model.greendao.gen.LocationNumberModelDao;
import com.atman.jixin.model.response.IntegralGoodsModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.personal.RegisterActivity;
import com.atman.jixin.utils.Common;
import com.atman.jixin.widget.EditTextDialog;
import com.atman.jixin.widget.ExchangeEditTextDialog;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.BaseNormalModel;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.PromptDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class IntegralexchangeActivity extends MyBaseActivity implements AdapterInterface
        ,ExchangeEditTextDialog.ETOnClick {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;

    private Context mContext = IntegralexchangeActivity.this;
    private View mEmpty;
    private TextView mEmptyTX;
    private IntegralExchangeAdapter mAdapter;
    private IntegralGoodsModel mIntegralGoodsModel;
    private ExchangeEditTextDialog dialog;

    private long storeId;
    private int myIntegral;
    private int mPage = 1;
    private int mSize = 20;
    private int cion;
    private long id;
    private int displayNum;
    private int totalNum = 0;
    private int tagNumId = 11;
    private int tagLocationId = 22;

    private LocationNumberModelDao mLocationNumberModelDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integralexchange);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long storeId, int myIntegral){
        Intent intent = new Intent(context, IntegralexchangeActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("myIntegral", myIntegral);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        mLocationNumberModelDao = MyBaseApplication.getApplication().getDaoSession().getLocationNumberModelDao();

        setBarRightIv(R.mipmap.exchangerecode_ic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ExchangeRecordActivity.buildIntent(mContext, storeId, "兑换记录"));
            }
        });

        storeId = getIntent().getLongExtra("storeId", -1);
        myIntegral = getIntent().getIntExtra("myIntegral", 0);
        if (myIntegral<0) {
            myIntegral = 0;
        }
        setBarTitleTx("积分商城("+myIntegral+")");

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_empty_view, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.part_empty_tx);
        mEmptyTX.setText("暂无商品可兑换");

        mAdapter = new IntegralExchangeAdapter(mContext, this);
        pullToRefreshListView.setEmptyView(mEmpty);
        pullToRefreshListView.setAdapter(mAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivityForResult(GoodsDetailActivity.buildIntent(mContext
                        , mAdapter.getItem(position-1).getGoodsId()
                        , mAdapter.getItem(position-1).getGoodsName()
                        , 1, myIntegral, mAdapter.getItem(position-1)), Common.TO_GOODSDETAIL);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.TO_GOODSDETAIL) {
            myIntegral = data.getIntExtra("myIntegral", 0);
            LogUtils.e("myIntegral:"+myIntegral);
            if (myIntegral<0) {
                myIntegral = 0;
            }
            setBarTitleTx("积分商城("+myIntegral+")");
            mPage = 1;
            mAdapter.clearData();
            dohttp(false);
        }
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        dohttp(true);
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        mPage = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        mPage += 1;
        dohttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        mPage = 1;
        mAdapter.clearData();
        dohttp(false);
    }

    private void dohttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Integral_Exchange + storeId + "/" + mPage + "/" + mSize)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_Integral_EXCHANGE_ID).id(Common.NET_Integral_EXCHANGE_ID).build()
                .execute(new MyStringCallback(mContext, "", this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_Integral_EXCHANGE_ID) {
            mIntegralGoodsModel = mGson.fromJson(data, IntegralGoodsModel.class);
            if (mIntegralGoodsModel.getBody() == null
                    || mIntegralGoodsModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                mAdapter.addBody(mIntegralGoodsModel.getBody());
            }
        } else if (id == Common.NET_EXCHANGE_ID) {
            BaseNormalModel temp = mGson.fromJson(data, BaseNormalModel.class);
            if (temp.getResult().equals("1")) {
                if (dialog!=null) {
                    dialog.dismiss();
                }
                showToast("商品兑换成功");
                myIntegral -= (cion*totalNum);
                LogUtils.e("myIntegral:"+myIntegral);
                if (myIntegral<0) {
                    myIntegral = 0;
                }
                setBarTitleTx("积分商城("+myIntegral+")");
                mPage = 1;
                mAdapter.clearData();
                dohttp(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_Integral_EXCHANGE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_EXCHANGE_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_integralgoods_exchange_ll:
                if (isVisitors()) {
                    showToBindPhone();
                    return;
                }
                if (mAdapter.getItem(position).getIntegral()>myIntegral) {
                    showWraning("对不起,您的积分不够兑换此商品!");
                } else {
                    String str = "本次可兑换数量:1";
                    if (mAdapter.getItem(position).getStoreLimit()>=1) {
                        if (mAdapter.getItem(position).getStoreLimit() >= mAdapter.getItem(position).getUserLimit()) {
                            if (mAdapter.getItem(position).getUserLimit()>1) {
                                str += "~" + mAdapter.getItem(position).getUserLimit();
                                displayNum = mAdapter.getItem(position).getUserLimit();
                            }
                        } else {
                            str += "~" + mAdapter.getItem(position).getStoreLimit();
                            displayNum = mAdapter.getItem(position).getStoreLimit();
                        }
                        cion = mAdapter.getItem(position).getIntegral();
                        id = mAdapter.getItem(position).getId();
                        //获取桌号
                        LocationNumberModel locationNumberModel = mLocationNumberModelDao.queryBuilder()
                                .where(LocationNumberModelDao.Properties.TargetId.eq(storeId)
                                        , LocationNumberModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();

                        String content = "";
                        if (locationNumberModel!=null && locationNumberModel.getLocation()!=null) {
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
        PromptDialog.Builder builder = new PromptDialog.Builder(IntegralexchangeActivity.this);
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
                        , IntegralexchangeActivity.this, true));
            }
        });
        builder.show();
    }

    private void showToBindPhone() {
        PromptDialog.Builder builder = new PromptDialog.Builder(IntegralexchangeActivity.this);
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
    public void onTouchOutside(EditText edittextDialogEt) {
        if (isIMOpen() && edittextDialogEt.getWindowToken()!=null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edittextDialogEt.getWindowToken(), 0); //强制隐藏键盘
        } else {
            dialog.dismiss();
        }
    }
}
