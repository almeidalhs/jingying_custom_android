package com.atman.jixin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.GetChatServiceModel;
import com.base.baselibs.iimp.AdapterInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/26 09:45
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ChatServiceAdapter extends BaseAdapter {

    private List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> list = null;
    private Context mContext;
    private AdapterInterface onItemClick;

    public ChatServiceAdapter(Context mContext, AdapterInterface onItemClick) {
        this.mContext = mContext;
        this.onItemClick = onItemClick;
        this.list = new ArrayList<>();
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return list.size();
    }

    public GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean mData = list.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_chatservice_listview, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.itemChatserviceTv.setText(mData.getOperaterName());

        return view;

    }

    static class ViewHolder {
        @Bind(R.id.item_chatservice_tv)
        TextView itemChatserviceTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
