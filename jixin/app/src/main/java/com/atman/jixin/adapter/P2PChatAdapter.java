package com.atman.jixin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.bean.ChatMessageModel;
import com.atman.jixin.model.iimp.ADChatType;
import com.atman.jixin.model.iimp.EventActionType;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.utils.face.SmileUtils;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.ShapeImageView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/1 16:06
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class P2PChatAdapter extends BaseAdapter {

    private List<ChatMessageModel> mImMessage;

    private Context context;
    protected LayoutInflater layoutInflater;
    private long time = 0;
    private PullToRefreshListView p2pChatLv;
    private boolean isBottom = false;
    private P2PAdapterInter mP2PAdapterInter;
    private int width;
    private long l1 = 5L;//定义一个long型变量
    private boolean leftChange = false;
    private boolean rightChange = false;
    private String leftImageUrl = "";
    private String rightImageUrl = "";
    private Handler handler;
    private Runnable runnable;

    public P2PChatAdapter(Context context, int width, PullToRefreshListView p2pChatLv
            , Handler handler, Runnable runnable, P2PAdapterInter mP2PAdapterInter) {
        this.context = context;
        this.mImMessage = new ArrayList<>();
        this.p2pChatLv = p2pChatLv;
        this.mP2PAdapterInter = mP2PAdapterInter;
        this.width = width;
        this.handler = handler;
        this.runnable = runnable;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setLeftImageUrl(String leftImageUrl) {
        this.leftImageUrl = leftImageUrl;
        notifyDataSetChanged();
    }

    public void setRightImageUrl(String rightImageUrl) {
        this.rightImageUrl = rightImageUrl;
        notifyDataSetChanged();
    }

    public void setLeftChange(boolean leftChange) {
        this.leftChange = leftChange;
    }

    public void setRightChange(boolean rightChange) {
        this.rightChange = rightChange;
    }

    public void clearData() {
        this.mImMessage.clear();
        notifyDataSetChanged();
    }

    public void addImMessageDao(List<ChatMessageModel> mImMessageDao) {
        this.mImMessage.addAll(mImMessageDao);
        notifyDataSetChanged();
        p2pChatLv.getRefreshableView().setSelection(p2pChatLv.getRefreshableView().getBottom());
    }

    public void setImMessageStatus(long id, int status) {
        for (int i = 0; i < mImMessage.size(); i++) {
            if (mImMessage.get(i).getId() == id) {
                mImMessage.get(i).setSendStatus(status);
            }
        }
        notifyDataSetChanged();
    }

    public void setImMessageAudio(long id, String url) {
        for (int i = 0; i < mImMessage.size(); i++) {
            if (mImMessage.get(i).getId() == id) {
                mImMessage.get(i).setAudioLocationUrl(url);
            }
        }
        notifyDataSetChanged();
    }

    public void setImMessageContent(long id, String content) {
        for (int i = 0; i < mImMessage.size(); i++) {
            if (mImMessage.get(i).getId() == id) {
                mImMessage.get(i).setContent(content);
            }
        }
        notifyDataSetChanged();
    }

    public void addImMessageDao(ChatMessageModel mImMessageDao) {
        if (!mImMessageDao.getSelfSend()
                && mImMessageDao.getOperaterExtra()!=null) {
            MyTools.copy(context, mImMessageDao.getOperaterExtra(), false);
        }
        this.mImMessage.add(mImMessageDao);
        notifyDataSetChanged();
        p2pChatLv.getRefreshableView().setSelection(p2pChatLv.getRefreshableView().getBottom());
    }

    @Override
    public int getViewTypeCount() {
        //要比种类数大一点
        return 6;
    }

    @Override
    public int getCount() {
        return mImMessage.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mImMessage.get(position).getType();
    }

    @Override
    public ChatMessageModel getItem(int position) {
        return mImMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holderText = null;

        int type = getItemViewType(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_p2pchat_text_view, parent, false);
            holderText = new ViewHolder(convertView);
            convertView.setTag(holderText);
        } else {
            holderText = (ViewHolder) convertView.getTag();
        }

        ChatMessageModel temp = mImMessage.get(position);

        holderText.itemP2pchatTextLeftTx.setVisibility(View.GONE);
        holderText.itemP2pchatImageLeftIv.setVisibility(View.GONE);
        holderText.itemP2pchatAudioLeftLl.setVisibility(View.GONE);
        holderText.itemP2pchatRightProgress.setVisibility(View.GONE);
        holderText.itemP2pchatRightAlert.setVisibility(View.GONE);

        holderText.itemP2pchatTextRightTx.setVisibility(View.GONE);
        holderText.itemP2pchatImageRightIv.setVisibility(View.GONE);
        holderText.itemP2pchatAudioRightLl.setVisibility(View.GONE);
        holderText.itemP2pchatImagetextLeftRl.setVisibility(View.GONE);
        if (temp.getSelfSend()) {
            holderText.itemP2pchatTextHeadrightIv.setVisibility(View.VISIBLE);
            holderText.itemP2pchatTextHeadleftIv.setVisibility(View.GONE);
            if (holderText.itemP2pchatTextHeadrightIv.getDrawable() == null || rightChange) {
                rightChange = false;
                String url = "";
                if (MyBaseApplication.USERINFOR.getBody().getMemberAvatar().contains("http")) {
                    url = MyBaseApplication.USERINFOR.getBody().getMemberAvatar();
                } else {
                    url = Common.ImageUrl + MyBaseApplication.USERINFOR.getBody().getMemberAvatar();
                }
                ImageLoader.getInstance().displayImage(url, holderText.itemP2pchatTextHeadrightIv
                        , MyBaseApplication.getApplication().optionsHead, mListener);
            }
        } else {
            holderText.itemP2pchatTextHeadrightIv.setVisibility(View.GONE);
            holderText.itemP2pchatTextHeadleftIv.setVisibility(View.VISIBLE);
            if (holderText.itemP2pchatTextHeadleftIv.getDrawable() == null || leftChange) {
                leftChange = false;
                String url = "";
                if (leftImageUrl.contains("http")) {
                    url = leftImageUrl;
                } else {
                    url = Common.ImageUrl + leftImageUrl;
                }
                ImageLoader.getInstance().displayImage(url, holderText.itemP2pchatTextHeadleftIv
                        , MyBaseApplication.getApplication().optionsHead, mListener);
            }
        }
        if (Math.abs(MyTools.getGapCountM(time, temp.getSendTime())) >= l1 || position == 0) {
            time = mImMessage.get(position).getSendTime();
            holderText.itemP2pchatTextTimeTx.setVisibility(View.VISIBLE);
            holderText.itemP2pchatTextTimeTx.setText(MyTools.convertTimeS(temp.getSendTime()));
        } else {
            holderText.itemP2pchatTextTimeTx.setVisibility(View.INVISIBLE);
        }
        if (temp.getSendStatus() == 2) {
            holderText.itemP2pchatRightProgress.setVisibility(View.VISIBLE);
        } else {
            holderText.itemP2pchatRightProgress.setVisibility(View.GONE);
            if (temp.getSendStatus() == 1) {
                holderText.itemP2pchatRightAlert.setVisibility(View.VISIBLE);
            } else {
                holderText.itemP2pchatRightAlert.setVisibility(View.GONE);
            }
        }

        switch (type) {
            case ADChatType.ADChatType_ImageText:
                if (temp.getSelfSend()) {} else {
                    holderText.itemP2pchatImagetextLeftRl.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatImagetextLeftTitleTx.setText(temp.getImageT_title());
                    if (temp.getActionType() == EventActionType.EventActionType_Enterprise) {
                        holderText.itemP2pchatImagetextLeftIcIv.setImageResource(R.mipmap.ic_chat_one);
                    } else if (temp.getActionType() == EventActionType.EventActionType_GoodList) {
                        holderText.itemP2pchatImagetextLeftIcIv.setImageResource(R.mipmap.ic_chat_three);
                    } else {
                        holderText.itemP2pchatImagetextLeftIcIv.setImageResource(R.mipmap.ic_chat_one);
                    }
                    String url = temp.getImageT_back();
                    if (url.equals("adchat_imagetext_image_coupon")) {
                        holderText.itemP2pchatImagetextLeftIv.setImageResource(R.mipmap.caidan0003);
                    } else {
                        if (!url.contains("http")) {
                            url = Common.ImageUrl + temp.getImageT_back();
                        }
                        File f = ImageLoader.getInstance().getDiskCache().get(url);
                        if (f.exists()) {
                            ImageLoader.getInstance().displayImage("file://" + f.getPath(), holderText.itemP2pchatImagetextLeftIv);
                        } else {
                            ImageLoader.getInstance().displayImage(url, holderText.itemP2pchatImagetextLeftIv
                                    , MyBaseApplication.getApplication().optionsHead, mListener);
                        }
                    }
                }
                break;
            case ADChatType.ADChatType_Text:
                if (temp.getSelfSend()) {
                    holderText.itemP2pchatTextRightTx.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatTextRightTx.setText(SmileUtils.getEmotionContent(context
                            , holderText.itemP2pchatTextRightTx, temp.getContent()));
                } else {
                    String tempStr = "";

                    if (temp.getOperaterName()!=null){
                        if (temp.getContent().contains("请选择下方")) {
                            tempStr = temp.getContent();
                        } else {
                            if (temp.getOperaterType()==3) {
                                tempStr = "你已经成功复制WiFi名称:"+temp.getOperaterName()+"的密码";
                            } else {
                                tempStr = temp.getContent();
                            }
                        }
                    } else {
                        tempStr = temp.getContent();
                    }


                    holderText.itemP2pchatTextLeftTx.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatTextLeftTx.setText(SmileUtils.getEmotionContent(context
                            , holderText.itemP2pchatTextRightTx, tempStr));
                }
                break;
            case ADChatType.ADChatType_Image:
                String url = temp.getContent();
                File f = new File(url);
                holderText.itemP2pchatImageRightIv.getLayoutParams().width = DensityUtil.dp2px(context, 100);
                holderText.itemP2pchatImageRightIv.getLayoutParams().height = DensityUtil.dp2px(context, 120);
                holderText.itemP2pchatImageRightIv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                if (f.exists()) {
                    if (temp.getSelfSend()) {
                        holderText.itemP2pchatImageRightIv.setVisibility(View.VISIBLE);
                        ImageLoader.getInstance().displayImage("file://" + f.getPath(), holderText.itemP2pchatImageRightIv);
                    } else {
                        holderText.itemP2pchatImageLeftIv.setVisibility(View.VISIBLE);
                        ImageLoader.getInstance().displayImage("file://" + f.getPath(), holderText.itemP2pchatImageLeftIv);
                    }
                } else {
                    url = Common.ImageUrl + url;
                    if (temp.getSelfSend()) {
                        holderText.itemP2pchatImageRightIv.setVisibility(View.VISIBLE);
                        ImageLoader.getInstance().displayImage(url, holderText.itemP2pchatImageRightIv
                                , MyBaseApplication.getApplication().optionsHead, mListenerPic);
                    } else {
                        holderText.itemP2pchatImageLeftIv.setVisibility(View.VISIBLE);
                        ImageLoader.getInstance().displayImage(url, holderText.itemP2pchatImageLeftIv
                                , MyBaseApplication.getApplication().optionsHead, mListenerPic);
                    }
                }
                break;
            case ADChatType.ADChatType_Audio:
                int w = (int) ((175 / 60 * temp.getAudio_duration()) + 75);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(DensityUtil.dp2px(context, w)
                        , FrameLayout.LayoutParams.WRAP_CONTENT);
                if (temp.getSelfSend()) {
                    holderText.itemP2pchatAudioRightLl.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatAudioRightLl.setLayoutParams(params);
                    holderText.itemP2pchatAudioRightTx.setText(temp.getAudio_duration() + "''");
                } else {
                    holderText.itemP2pchatAudioLeftLl.setLayoutParams(params);
                    holderText.itemP2pchatAudioLeftLl.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatAudioLeftTx.setText(temp.getAudio_duration() + "''");
                }
                break;
        }

        AnimationDrawable  animationDrawableRight = (AnimationDrawable) holderText.itemP2pchatAudioRightIv.getDrawable();
        animationDrawableRight.stop();
        animationDrawableRight.selectDrawable(0);
        AnimationDrawable  animationDrawable = (AnimationDrawable) holderText.itemP2pchatAudioLeftIv.getDrawable();
        animationDrawable.stop();
        animationDrawable.selectDrawable(0);

        final ViewHolder finalHolderRightText = holderText;
        holderText.itemP2pchatAudioRightLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItemAudio(v, position, (AnimationDrawable) finalHolderRightText.itemP2pchatAudioRightIv.getDrawable());
            }
        });

        final ViewHolder finalHolderLeftText = holderText;
        holderText.itemP2pchatAudioLeftLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItemAudio(v, position, (AnimationDrawable) finalHolderLeftText.itemP2pchatAudioLeftIv.getDrawable());
            }
        });

        holderText.itemP2pchatImageRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItem(v, position);
            }
        });
        holderText.itemP2pchatImageLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItem(v, position);
            }
        });

        holderText.itemP2pchatRootRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItem(v, position);
            }
        });

        holderText.itemP2pchatTextHeadleftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItem(v, position);
            }
        });

        holderText.itemP2pchatImagetextLeftRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItem(v, position);
            }
        });

        return convertView;
    }

    private ImageLoadingListener mListener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            if (!isBottom) {
                isBottom = true;
                handler.postDelayed(runnable, 500);
            }
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    };

    private ImageLoadingListener mListenerPic = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            ImageView im = (ImageView) view;
            int w = DensityUtil.dp2px(context, 100) - DensityUtil.dp2px(context, 10);
            int h = w * bitmap.getHeight()/bitmap.getWidth();
            im.getLayoutParams().height = h;
            im.getLayoutParams().width = w;
            if (!isBottom) {
                isBottom = true;
                handler.postDelayed(runnable, 500);
            }
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    };

    public interface P2PAdapterInter {
        void onItem(View v, int position);

        void onItemAudio(View v, int position, AnimationDrawable animationDrawable);
    }

    static class ViewHolder {
        @Bind(R.id.item_p2pchat_text_time_tx)
        TextView itemP2pchatTextTimeTx;
        @Bind(R.id.item_p2pchat_text_headleft_iv)
        ShapeImageView itemP2pchatTextHeadleftIv;
        @Bind(R.id.item_p2pchat_text_headright_iv)
        ShapeImageView itemP2pchatTextHeadrightIv;
        @Bind(R.id.item_p2pchat_text_left_tx)
        TextView itemP2pchatTextLeftTx;
        @Bind(R.id.item_p2pchat_image_left_iv)
        ImageView itemP2pchatImageLeftIv;
        @Bind(R.id.item_p2pchat_imagetext_left_iv)
        ImageView itemP2pchatImagetextLeftIv;
        @Bind(R.id.item_p2pchat_imagetext_left_ic_iv)
        ImageView itemP2pchatImagetextLeftIcIv;
        @Bind(R.id.item_p2pchat_imagetext_left_title_tx)
        TextView itemP2pchatImagetextLeftTitleTx;
        @Bind(R.id.item_p2pchat_imagetext_left_rl)
        RelativeLayout itemP2pchatImagetextLeftRl;
        @Bind(R.id.item_p2pchat_audio_left_iv)
        ImageView itemP2pchatAudioLeftIv;
        @Bind(R.id.item_p2pchat_audio_left_tx)
        TextView itemP2pchatAudioLeftTx;
        @Bind(R.id.item_p2pchat_audio_left_ll)
        LinearLayout itemP2pchatAudioLeftLl;
        @Bind(R.id.item_p2pchat_right_progress)
        ProgressBar itemP2pchatRightProgress;
        @Bind(R.id.item_p2pchat_right_alert)
        ImageView itemP2pchatRightAlert;
        @Bind(R.id.item_p2pchat_text_right_tx)
        TextView itemP2pchatTextRightTx;
        @Bind(R.id.item_p2pchat_image_right_iv)
        ImageView itemP2pchatImageRightIv;
        @Bind(R.id.item_p2pchat_audio_right_tx)
        TextView itemP2pchatAudioRightTx;
        @Bind(R.id.item_p2pchat_audio_right_iv)
        ImageView itemP2pchatAudioRightIv;
        @Bind(R.id.item_p2pchat_audio_right_ll)
        LinearLayout itemP2pchatAudioRightLl;
        @Bind(R.id.item_p2pchat_root_Rl)
        RelativeLayout itemP2pchatRootRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
