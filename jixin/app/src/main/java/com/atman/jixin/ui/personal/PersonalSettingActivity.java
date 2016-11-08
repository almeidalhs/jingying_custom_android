package com.atman.jixin.ui.personal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.greendao.gen.ChatListModelDao;
import com.atman.jixin.model.greendao.gen.ChatMessageModelDao;
import com.atman.jixin.model.response.CheckVersionModel;
import com.atman.jixin.model.response.LoginResultModel;
import com.atman.jixin.ui.MainActivity;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DataCleanManager;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.PromptDialog;
import com.base.baselibs.widget.switchbutton.SwitchButton;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/18.
 */

public class PersonalSettingActivity extends MyBaseActivity {

    @Bind(R.id.setting_open_sb)
    SwitchButton settingOpenSb;
    @Bind(R.id.personal_peoplenearby_ll)
    LinearLayout personalPeoplenearbyLl;
    @Bind(R.id.personal_clean_cachedata_tx)
    TextView personalCleanCachedataTx;
    @Bind(R.id.personal_clean_cachedata_ll)
    LinearLayout personalCleanCachedataLl;
    @Bind(R.id.personal_clean_all_ll)
    LinearLayout personalCleanAllLl;
    @Bind(R.id.personal_version_tx)
    TextView personalVersionTx;
    @Bind(R.id.personal_version_ll)
    LinearLayout personalVersionLl;

    private Context mContext = PersonalSettingActivity.this;
    private String mCacheSize;
    private String mCachePath = "/data/data/com.atman.jixin/";
    private int setStatus;

    private ChatListModelDao mChatListModelDao;
    private ChatMessageModelDao mChatMessageModelDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalsetting);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        mChatListModelDao = MyBaseApplication.getApplication().getDaoSession().getChatListModelDao();
        mChatMessageModelDao = MyBaseApplication.getApplication().getDaoSession().getChatMessageModelDao();

        setBarTitleTx("个人设置");
        personalVersionTx.setText(MyBaseApplication.VERSION);
        personalVersionLl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showToast("渠道：" + MyBaseApplication.CHANNEL);
                return true;
            }
        });
        setSwitchButton();
        settingOpenSb.setCanMoveChang(false);
        settingOpenSb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Integer> p = new HashMap<>();
                p.put("showNear", setStatus);
                OkHttpUtils.postString().url(Common.Url_Manage).tag(Common.NET_MANAGE_ID)
                        .id(Common.NET_MANAGE_ID).content(mGson.toJson(p)).mediaType(Common.JSON)
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .build().execute(new MyStringCallback(mContext, "", PersonalSettingActivity.this, true));
            }
        });
    }

    private void setSwitchButton() {
        if (MyBaseApplication.USERINFOR.getBody().getShowNear()==0) {
            settingOpenSb.setCheckedImmediately(true);
            setStatus = 1;
        } else {
            settingOpenSb.setCheckedImmediately(false);
            setStatus = 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        countCache();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_LOGOUT_ID) {
            showToast("已退出登录");
            clearData();
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else if (id == Common.NET_MANAGE_ID) {
            LoginResultModel mLoginResultModel = mGson.fromJson(data, LoginResultModel.class);
            MyBaseApplication.USERINFOR = mLoginResultModel;
            setSwitchButton();
        } else if (id == Common.NET_GET_VERSION_ID) {
            final CheckVersionModel mCheckVersionModel = mGson.fromJson(data, CheckVersionModel.class);
            if (mCheckVersionModel.getResult().equals("1") && mCheckVersionModel.getBody()!=null) {
                PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
                builder.setMessage(mCheckVersionModel.getBody().getWarn());
                builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final File updateFile = createFile(MyBaseApplication.VERSION);
                        if (updateFile != null) {
                            /**调用系统浏览器在页面中下载*/
                            Uri uri = Uri.parse(mCheckVersionModel.getBody().getUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            showToast("SD卡路径错误，无法下载");
                        }
                    }
                });
                if (mCheckVersionModel.getBody().getForce().equals("0")) {
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    builder.setCancelable(false);
                }
                builder.show();
            } else {
                showToast("已是最新版本");
            }
        }
    }

    private void countCache() {
        try {
            mCacheSize = DataCleanManager.getCacheSize(new File(mCachePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        personalCleanCachedataTx.setText(mCacheSize);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_LOGOUT_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_MANAGE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_VERSION_ID);
    }

    @OnClick({R.id.personal_clean_cachedata_ll, R.id.personal_clean_all_ll, R.id.personal_version_ll
            , R.id.personal_logout_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_clean_cachedata_ll:
                showCleanData();
                break;
            case R.id.personal_clean_all_ll:
                showCleanAllData();
                break;
            case R.id.personal_version_ll:
                showToast("检查更新版本...");
                OkHttpUtils.get().url(Common.Url_Get_Version+"?version="+MyBaseApplication.VERSION)
                        .tag(Common.NET_GET_VERSION_ID).id(Common.NET_GET_VERSION_ID)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .build().execute(new MyStringCallback(mContext, "检查更新版本...", PersonalSettingActivity.this, true));
                break;
            case R.id.personal_logout_bt:
                showExit();
                break;
        }
    }

    private void showCleanAllData() {
        PromptDialog.Builder builder = new PromptDialog.Builder(PersonalSettingActivity.this);
        builder.setMessage("你确定清除所有聊天记录吗？");
        builder.setPositiveButton("清除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mChatListModelDao.deleteAll();
                mChatMessageModelDao.deleteAll();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showCleanData() {
        PromptDialog.Builder builder = new PromptDialog.Builder(PersonalSettingActivity.this);
        builder.setMessage("你确定清除应用缓存吗？");
        builder.setPositiveButton("清除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataCleanManager.cleanApplicationData(mContext, mCachePath);
                countCache();
                personalCleanCachedataTx.setText("0 Byte");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showExit() {
        PromptDialog.Builder builder = new PromptDialog.Builder(PersonalSettingActivity.this);
        builder.setMessage("你确定要退出登录吗？");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OkHttpUtils.postString().url(Common.Url_Logout).tag(Common.NET_LOGOUT_ID)
                        .id(Common.NET_LOGOUT_ID).content("{logout}").mediaType(Common.JSON)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .build().execute(new MyStringCallback(mContext, "", PersonalSettingActivity.this, true));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
