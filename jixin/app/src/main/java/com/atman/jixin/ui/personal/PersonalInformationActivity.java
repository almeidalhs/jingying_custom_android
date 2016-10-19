package com.atman.jixin.ui.personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

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
            if (MyBaseApplication.USERINFOR.getBody().getMobileSign()!=null) {
                personalinforSignatrueTx.setText(MyBaseApplication.USERINFOR.getBody().getMobileSign());
            } else {
                personalinforSignatrueTx.setText("");
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
        super.onStringResponse(data, response, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                break;
            case R.id.personalinfor_gander_ll:
                break;
            case R.id.personalinfor_birthday_ll:
                break;
            case R.id.personalinfor_display_ll:
                break;
            case R.id.personalinfor_signatrue_ll:
                break;
            case R.id.personalinfor_emotionstatus_ll:
                break;
            case R.id.personalinfor_occupation_ll:
                break;
            case R.id.personalinfor_hobby_ll:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }
}
