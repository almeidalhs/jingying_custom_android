package com.atman.jixin.ui.personal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.HeadImgResultModel;
import com.atman.jixin.model.response.LoginResultModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.utils.UiHelper;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/19.
 */

public class PersonalInformationActivity extends MyBaseActivity {

    @Bind(R.id.personalinfor_aount_tx)
    TextView personalinforAountTx;
    @Bind(R.id.personalinfor_nick_tx)
    TextView personalinforNickTx;
    @Bind(R.id.personalinfor_gander_tx)
    TextView personalinforGanderTx;
    @Bind(R.id.personalinfor_birthday_tx)
    TextView personalinforBirthdayTx;
    @Bind(R.id.personalinfor_display_tx)
    TextView personalinforDisplayTx;
    @Bind(R.id.personalinfor_signatrue_tx)
    TextView personalinforSignatrueTx;
    @Bind(R.id.personalinfor_emotionstatus_tx)
    TextView personalinforEmotionstatusTx;
    @Bind(R.id.personalinfor_occupation_tx)
    TextView personalinforOccupationTx;
    @Bind(R.id.personalinfor_hobby_tx)
    TextView personalinforHobbyTx;
    @Bind(R.id.personalinfor_headimg_iv)
    ShapeImageView personalinforHeadimgIv;

    private Context mContext = PersonalInformationActivity.this;
    private String allStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinformation);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("个人信息");
    }

    private void updataView() {
        if (MyBaseApplication.USERINFOR!=null) {
            personalinforAountTx.setText(MyBaseApplication.USERINFOR.getBody().getMemberMobile());
            personalinforNickTx.setText(MyBaseApplication.USERINFOR.getBody().getMemberName());
            ImageLoader.getInstance().displayImage(Common.ImageUrl
                    + MyBaseApplication.USERINFOR.getBody().getMemberAvatar(), personalinforHeadimgIv
                    , MyBaseApplication.getApplication().optionsHead);
            if (MyBaseApplication.USERINFOR.getBody().getMemberSex()==0) {
                personalinforGanderTx.setText("女");
            } else {
                personalinforGanderTx.setText("男");
            }
            if (MyBaseApplication.USERINFOR.getBody().getMemberBirthday()!=null) {
                if (MyBaseApplication.USERINFOR.getBody().getMemberBirthday().contains("-")) {
                    personalinforBirthdayTx.setText(MyBaseApplication.USERINFOR.getBody()
                            .getMemberBirthday().substring(0,10));
                } else {
                    LogUtils.e(">>:"+Long.parseLong(MyBaseApplication.USERINFOR.getBody()
                            .getMemberBirthday())/1000);
                    personalinforBirthdayTx.setText(MyTools.convertTime(Long.parseLong(MyBaseApplication.USERINFOR.getBody()
                            .getMemberBirthday()), "yyyy-MM-dd"));
                }
            } else {
                personalinforBirthdayTx.setText("");
            }
            if (MyBaseApplication.USERINFOR.getBody().getAroundSite()!=null) {
                personalinforDisplayTx.setText(MyBaseApplication.USERINFOR.getBody().getAroundSite());
            } else {
                personalinforDisplayTx.setText("");
            }
            if (MyBaseApplication.USERINFOR.getBody().getMemberSign()!=null) {
                personalinforSignatrueTx.setText(MyBaseApplication.USERINFOR.getBody().getMemberSign());
            } else {
                personalinforSignatrueTx.setText("这家伙很忙,还未来得及设置签名!");
            }
            if (MyBaseApplication.USERINFOR.getBody().getMaritalStatus()!=null) {
                personalinforEmotionstatusTx.setText(MyBaseApplication.USERINFOR.getBody().getMaritalStatus());
            } else {
                personalinforEmotionstatusTx.setText("");
            }
            if (MyBaseApplication.USERINFOR.getBody().getJob()!=null) {
                personalinforOccupationTx.setText(MyBaseApplication.USERINFOR.getBody().getJob());
            } else {
                personalinforOccupationTx.setText("");
            }
            if (MyBaseApplication.USERINFOR.getBody().getInterest()!=null) {
                personalinforHobbyTx.setText(MyBaseApplication.USERINFOR.getBody().getInterest());
            } else {
                personalinforHobbyTx.setText("");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updataView();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        if (id == Common.NET_MANAGE_ID || id == Common.NET_MODFY_HEADIMG_ID) {
            super.onStringResponse(data, response, id);
            LoginResultModel mLoginResultModel = mGson.fromJson(data, LoginResultModel.class);
            if (mLoginResultModel.getResult().equals("1")) {
                MyBaseApplication.USERINFOR = mLoginResultModel;
                PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_PW, mLoginResultModel.getBody().getMemberPasswd());
                PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_USERID, mLoginResultModel.getBody().getAtmanUserId()+"");
                PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_USER_IMG, mLoginResultModel.getBody().getMemberAvatar());
                showToast(allStr+"修改成功");
                updataView();
            } else {
                showToast(allStr+"修改失败");
            }
        } else if (id == Common.NET_HEADIMG_ID) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel.getResult().equals("0")) {
                super.onStringResponse(data, response, id);
                showToast("头像修改失败");
            } else {
                String str = mHeadImgResultModel.getBody().get(0).getUrl();
                Map<String, String> p = new HashMap<>();
                p.put("avatar", str);
                OkHttpUtils.postString().url(Common.Url_Modify_HeadImg).tag(Common.NET_MODFY_HEADIMG_ID)
                        .id(Common.NET_MODFY_HEADIMG_ID).content(mGson.toJson(p)).mediaType(Common.JSON)
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .build().execute(new MyStringCallback(mContext, "修改中...", PersonalInformationActivity.this, false));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_MANAGE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_HEADIMG_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_MODFY_HEADIMG_ID);
    }

    @OnClick({R.id.personalinfor_resetpwd_ll, R.id.personalinfor_nick_ll, R.id.personalinfor_headimg_ll
            , R.id.personalinfor_gander_ll, R.id.personalinfor_birthday_ll, R.id.personalinfor_display_ll
            , R.id.personalinfor_signatrue_ll, R.id.personalinfor_emotionstatus_ll, R.id.personalinfor_occupation_ll, R.id.personalinfor_hobby_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personalinfor_resetpwd_ll:
                startActivity(new Intent(mContext, ResetPassWordActivity.class));
                break;
            case R.id.personalinfor_nick_ll:
                startActivity(new Intent(mContext, ModifyNickActivity.class));
                break;
            case R.id.personalinfor_headimg_ll:
                allStr = "头像";
                showHeadImg();
                break;
            case R.id.personalinfor_gander_ll:
                allStr = "性别";
                showGanderDialog();
                break;
            case R.id.personalinfor_birthday_ll:
                break;
            case R.id.personalinfor_display_ll:
                startActivity(new Intent(mContext, ModifyDisplayActivity.class));
                break;
            case R.id.personalinfor_signatrue_ll:
                startActivity(new Intent(mContext, ModifySignatrueActivity.class));
                break;
            case R.id.personalinfor_emotionstatus_ll:
                allStr = "情感状态";
                showEmotionDialog();
                break;
            case R.id.personalinfor_occupation_ll:
                startActivity(new Intent(mContext, ModifyJobActivity.class));
                break;
            case R.id.personalinfor_hobby_ll:
                startActivity(new Intent(mContext, ModifyHobbyActivity.class));
                break;
        }
    }

    private void showGanderDialog() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">选择性别</font>"));
        builder.setItems(new String[]{"男", "女"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = "";
                if (which == 0) {
                    str = "男";
                } else {
                    str = "女";
                }
                Map<String, String> p = new HashMap<>();
                p.put("memberSex", str);
                modifyPersonalInfor(p, true);
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showEmotionDialog() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">选择情感状态</font>"));
        builder.setItems(new String[]{"已婚", "单身", "恋爱中", "保密"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = "";
                if (which == 0) {
                    str = "已婚";
                } else if (which == 1) {
                    str = "单身";
                } else if (which == 2) {
                    str = "恋爱中";
                } else {
                    str = "保密";
                }
                Map<String, String> p = new HashMap<>();
                p.put("maritalStatus", str);
                modifyPersonalInfor(p, true);
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private Uri imageUri;//The Uri to store the big bitmap
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private final int CROP_BIG_PICTURE = 666;
    private int outputX = 350;
    private String path = "";

    private void showHeadImg() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">头像修改</font>"));
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {//拍照
                    path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                } else {//选择照片
                    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                    getAlbum.setType("image/*");
                    startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
                }
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void modifyPersonalInfor(Map<String, String> p, boolean b) {
        OkHttpUtils.postString().url(Common.Url_Manage).tag(Common.NET_MANAGE_ID)
                .id(Common.NET_MANAGE_ID).content(mGson.toJson(p))
                .mediaType(Common.JSON)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .build().execute(new MyStringCallback(mContext, "修改中...", PersonalInformationActivity.this, b));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            imageUri = data.getData();
            cropImageUri(imageUri, outputX, outputX, CROP_BIG_PICTURE);
        } else if (requestCode == TAKE_BIG_PICTURE) {
            imageUri = Uri.parse("file:///" + path);
            cropImageUri(imageUri, outputX, outputX, CROP_BIG_PICTURE);
        } else if (requestCode == CROP_BIG_PICTURE) {
            if (imageUri != null) {
                OkHttpUtils.post().url(Common.Url_HeadImg)
                        .addParams("uploadType", "img").addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .addFile("files0_name", StringUtils.getFileName(imageUri.getPath()), new File(imageUri.getPath()))
                        .id(Common.NET_HEADIMG_ID).tag(Common.NET_HEADIMG_ID)
                        .build().execute(new MyStringCallback(PersonalInformationActivity.this, "修改中...", this, true));
            }
        }
    }

    //裁减照片
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        if (uri == null) {
            return;
        }
        LogUtils.e("outputX:"+outputX+",outputY:"+outputY);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        imageUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }
}
