package com.atman.jixin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.bean.ChatListModel;
import com.atman.jixin.model.iimp.ADChatType;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.utils.face.SmileUtils;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/12 13:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MessageSessionListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<ChatListModel> dataList;
    private AdapterInterface mAdapterInterface;

    public MessageSessionListAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
    }

    public void addBody(ChatListModel data) {
        this.dataList.clear();
        this.dataList.add(data);
        notifyDataSetChanged();
    }

    public void addBody(List<ChatListModel> data) {
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearUnreadNum(int p) {
        this.dataList.get(p).setUnreadNum(0);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public ChatListModel getItem(int position) {
        return dataList.get(position);
    }

    public void deleteItemById(int id) {
        dataList.remove(id);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_session_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (dataList.get(position).getTargetAvatar() != null) {
            if (dataList.get(position).getTargetAvatar().isEmpty()) {
                holder.itemSessionHeadIv.setBackgroundResource(R.mipmap.img_sign_logo);
            } else {
                String url = "";
                if (dataList.get(position).getTargetAvatar().toString().contains("http")) {
                    url = dataList.get(position).getTargetAvatar();
                } else {
                    url = Common.ImageUrl + dataList.get(position).getTargetAvatar();
                }
                ImageLoader.getInstance().displayImage(url, holder.itemSessionHeadIv
                        , MyBaseApplication.getApplication().optionsHead);
            }
        } else {
            holder.itemSessionHeadIv.setBackgroundResource(R.mipmap.img_sign_logo);
        }

        holder.itemSessionNickTx.setText(dataList.get(position).getTargetName());
        holder.itemSessionTimeTx.setText(MyTools.convertTimeS(dataList.get(position).getSendTime()));

        if (dataList.get(position).getUnreadNum() > 0) {
            holder.itemSessionUnreadTx.setVisibility(View.VISIBLE);
            if (dataList.get(position).getUnreadNum() > 99) {
                holder.itemSessionUnreadTx.setText("99+");
            } else {
                holder.itemSessionUnreadTx.setText(dataList.get(position).getUnreadNum() + "");
            }
        } else {
            holder.itemSessionUnreadTx.setVisibility(View.INVISIBLE);
        }

        String content = "";
        if (dataList.get(position).getType() == ADChatType.ADChatType_Text) {
            content = dataList.get(position).getContent();
        } else if (dataList.get(position).getType() == ADChatType.ADChatType_Image) {
            content = "[图片]";
        } else if (dataList.get(position).getType() == ADChatType.ADChatType_Audio) {
            content = "[语音]";
        } else if (dataList.get(position).getType() == ADChatType.ADChatType_Video) {
            content = "[视频]";
        } else if (dataList.get(position).getType() == ADChatType.ADChatType_ImageText) {
            content = "[图片]";
        } else {
            content = dataList.get(position).getContent();
        }
        holder.itemSessionContentTx.setText(SmileUtils.getEmotionContent(context
                , holder.itemSessionContentTx, content));

        return convertView;
    }

    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_session_head_iv)
        ShapeImageView itemSessionHeadIv;
        @Bind(R.id.item_session_unread_tx)
        TextView itemSessionUnreadTx;
        @Bind(R.id.item_session_nick_tx)
        TextView itemSessionNickTx;
        @Bind(R.id.item_session_time_tx)
        TextView itemSessionTimeTx;
        @Bind(R.id.item_session_content_tx)
        TextView itemSessionContentTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
