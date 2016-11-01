package com.atman.jixin.ui.im.chatui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.iimp.CommentType;
import com.atman.jixin.model.response.GetCompanyIntrodutionModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/11/1.
 */

public class CompanyIntroductionActivity extends MyBaseActivity {

    @Bind(R.id.company_introduction_tx)
    TextView companyIntroductionTx;
    @Bind(R.id.company_top_iv)
    ImageView companyTopIv;
    @Bind(R.id.company_top_rl)
    RelativeLayout companyTopRl;

    private Context mContext = CompanyIntroductionActivity.this;

    private String title;
    private String imgUrl;
    private long id;

    private GetCompanyIntrodutionModel mGetCompanyIntrodutionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyintroduction);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String title, long id, String imgUrl) {
        Intent intent = new Intent(context, CompanyIntroductionActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        intent.putExtra("imgUrl", imgUrl);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        title = getIntent().getStringExtra("title");
        imgUrl = getIntent().getStringExtra("imgUrl");
        id = getIntent().getLongExtra("id", -1);

        LogUtils.e("id:" + id + ",title:" + title + ",imgUrl:" + imgUrl);

        setBarTitleTx(title);
        setBarRightTx("评论").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StoreCommentActivity.buildIntent(mContext, id, CommentType.CommentType_STORE));
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(),
                getmWidth() * 9 / 16);
        companyTopRl.setLayoutParams(params);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Store_Introduction + id)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_STORE_INRRODUCTION_ID).id(Common.NET_GET_STORE_INRRODUCTION_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_STORE_INRRODUCTION_ID) {
            mGetCompanyIntrodutionModel = mGson.fromJson(data, GetCompanyIntrodutionModel.class);

            companyIntroductionTx.setText(mGetCompanyIntrodutionModel.getBody().getFileList().get(0).getRemark());
            String url = mGetCompanyIntrodutionModel.getBody().getFileList().get(0).getShowImg();
            if (!url.startsWith("http")) {
                url = Common.ImageUrl + mGetCompanyIntrodutionModel.getBody().getFileList().get(0).getShowImg();
            }
            ImageLoader.getInstance().displayImage(url, companyTopIv, MyBaseApplication.getApplication().optionsNot);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_STORE_INRRODUCTION_ID);
    }

    @OnClick({R.id.company_top_iv, R.id.company_top_start_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company_top_iv:
                break;
            case R.id.company_top_start_iv:
                break;
        }
    }
}
