package com.atman.jixin.ui.im.chatui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.StoreCommentAdapter;
import com.atman.jixin.model.response.AddCommentModel;
import com.atman.jixin.model.response.StoreCommentModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.TAPersonalInformationActivity;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.face.FaceRelativeLayout;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/11/1.
 */

public class StoreCommentActivity extends MyBaseActivity implements AdapterInterface, EditCheckBack {

    @Bind(R.id.p2pchat_lv)
    PullToRefreshListView p2pchatLv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.p2pchat_send_bt)
    Button p2pchatSendBt;
    @Bind(R.id.ll1)
    LinearLayout ll1;
    @Bind(R.id.vp_contains)
    ViewPager vpContains;
    @Bind(R.id.iv_image)
    LinearLayout ivImage;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.FaceRelativeLayout)
    FaceRelativeLayout FaceRelativeLayout;
    @Bind(R.id.item_p2pchat_root_ll)
    LinearLayout itemP2pchatRootLl;
    private View mEmpty;
    private TextView mEmptyTX;

    private Context mContext = StoreCommentActivity.this;

    private StoreCommentModel mStoreCommentModel;
    private StoreCommentAdapter mAdapter;
    private long id;
    private int typeId;
    private int mPage = 1;
    private int mSize = 20;
    private int allPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storecomment);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long id, int typeId) {
        Intent intent = new Intent(context, StoreCommentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("typeId", typeId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("评论");

        id = getIntent().getLongExtra("id", -1);
        typeId = getIntent().getIntExtra("typeId", 2);

        blogdetailAddcommentEt.addTextChangedListener(new MyTextWatcherTwo(this));

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, p2pchatLv);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_empty_view, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.part_empty_tx);
        mEmptyTX.setText("暂无评论");

        mAdapter = new StoreCommentAdapter(mContext, this);
        p2pchatLv.setEmptyView(mEmpty);
        p2pchatLv.setAdapter(mAdapter);
        p2pchatLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isIMOpen() && view.getWindowToken()!=null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                }
                llFacechoose.setVisibility(View.GONE);
                blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_face);
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        dohttp(true);
    }

    private void dohttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_Comment + id + "/" + typeId + "/" +mPage+ "/" +mSize)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_COMMENT_ID).id(Common.NET_GET_COMMENT_ID).build()
                .execute(new MyStringCallback(mContext, "", this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        if (id == Common.NET_GET_COMMENT_ID) {
            mPage = 1;
            onLoad(PullToRefreshBase.Mode.BOTH, p2pchatLv);
        } else if (id == Common.NET_COMMENT_LIKE_ID) {
            mAdapter.setNotLikeItemById(allPosition);
        }
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

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_COMMENT_ID) {
            mStoreCommentModel = mGson.fromJson(data, StoreCommentModel.class);
            if (mStoreCommentModel.getBody() == null || mStoreCommentModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, p2pchatLv);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, p2pchatLv);
                mAdapter.addBody(mStoreCommentModel.getBody());
            }
        } else if (id == Common.NET_COMMENT_LIKE_ID) {

        } else if (id == Common.NET_ADD_COMMENT_ID) {
            AddCommentModel mAddCommentModel = mGson.fromJson(data, AddCommentModel.class);
            StoreCommentModel.BodyBean temp = new StoreCommentModel.BodyBean();
            temp.setId(mAddCommentModel.getBody().getId());
            temp.setAddTime(System.currentTimeMillis());
            temp.setContent(mAddCommentModel.getBody().getContent());
            temp.setUserAvatar(MyBaseApplication.USERINFOR.getBody().getMemberAvatar());
            temp.setUserName(MyBaseApplication.USERINFOR.getBody().getMemberName());
            temp.setUserId(mAddCommentModel.getBody().getUserId());
            mAdapter.addBody(temp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_COMMENT_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_COMMENT_LIKE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_COMMENT_ID);
    }

    @OnClick({R.id.blogdetail_addemol_iv, R.id.p2pchat_send_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.blogdetail_addemol_iv:
                if (llFacechoose.getVisibility() == View.GONE) {
                    if (isIMOpen()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    }
                    llFacechoose.setVisibility(View.VISIBLE);
                    blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_keyboard);
                } else {
                    llFacechoose.setVisibility(View.GONE);
                    blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_face);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                }
                break;
            case R.id.p2pchat_send_bt:
                if (TextUtils.isEmpty(blogdetailAddcommentEt.getText().toString())) {
                    showToast("请输入评论内容!");
                    return;
                }
                Map<String, String> p = new HashMap<>();
                p.put("content", blogdetailAddcommentEt.getText().toString());
                p.put("type", String.valueOf(typeId));
                p.put("objId", String.valueOf(id));
                OkHttpUtils.postString().url(Common.Url_Add_Comment).tag(Common.NET_ADD_COMMENT_ID)
                        .id(Common.NET_ADD_COMMENT_ID).content(mGson.toJson(p)).mediaType(Common.JSON)
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .build().execute(new MyStringCallback(mContext, "评论中...", StoreCommentActivity.this, false));
                blogdetailAddcommentEt.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_comment_like_tx:
                if (mAdapter.getItem(position).getIsLike()==0) {
                    allPosition = position;
                    OkHttpUtils.postString().url(Common.Url_Comment_Like+mAdapter.getItem(position).getId()).tag(Common.NET_COMMENT_LIKE_ID)
                        .id(Common.NET_COMMENT_LIKE_ID).content("{}").mediaType(Common.JSON)
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .build().execute(new MyStringCallback(mContext, "处理中...", StoreCommentActivity.this, false));

                    mAdapter.setLikeItemById(position);
                }
                break;
            case R.id.item_comment_head_iv:
                if (mAdapter.getItem(position).getStoreId()==0) {
                    if (mAdapter.getItem(position).getUserId()
                            == MyBaseApplication.USERINFOR.getBody().getAtmanUserId()) {
                        showWraning("亲,这是您自己哦!");
                        return;
                    }
                    startActivity(TAPersonalInformationActivity.buildIntent(mContext
                            , mAdapter.getItem(position).getUserId()));
                } else {
                    startActivity(StoreDetailActivity.buildIntent(mContext
                            , mAdapter.getItem(position).getStoreId()));
                }
                break;
        }
    }

    @Override
    public void isNull() {
    }
}
