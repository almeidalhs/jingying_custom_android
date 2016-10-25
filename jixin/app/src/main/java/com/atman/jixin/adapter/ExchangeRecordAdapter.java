package com.atman.jixin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.ExchangeRecordModel;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.base.baselibs.iimp.AdapterInterface;
import com.nostra13.universalimageloader.core.ImageLoader;

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
public class ExchangeRecordAdapter extends BaseAdapter {

    private List<ExchangeRecordModel.BodyBean> list = null;
    private Context mContext;
    private AdapterInterface onItemClick;

    public ExchangeRecordAdapter(Context mContext, AdapterInterface onItemClick) {
        this.mContext = mContext;
        this.onItemClick = onItemClick;
        this.list = new ArrayList<>();
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<ExchangeRecordModel.BodyBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return list.size();
    }

    public ExchangeRecordModel.BodyBean getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final ExchangeRecordModel.BodyBean mData = list.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_exchangerecord_view, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance().displayImage(Common.ImageUrl + mData.getGoodsImage()
                , viewHolder.itemExchangerecordIv, MyBaseApplication.getApplication().optionsHead);
        viewHolder.itemExchangerecordNameTx.setText(mData.getGoodsName());
        viewHolder.itemExchangerecordIntegralTx.setText("积分: " + mData.getIntegral());
        viewHolder.itemExchangerecordNumTx.setText("兑换数量:" + mData.getAmount());
        viewHolder.itemExchangerecordTimeTx.setText(MyTools.convertTime(mData.getAddTime()* 1000, "yyyy-MM-dd HH:mm"));
        //0-取消兑换 1-待处理 2-兑换成功
        if (mData.getState()==1) {
            viewHolder.itemExchangerecordStatusTx.setText("处理中");
        } else if (mData.getState()==1) {
            viewHolder.itemExchangerecordStatusTx.setText("已取消");
        } else if (mData.getState()==2) {
            viewHolder.itemExchangerecordStatusTx.setText("兑换成功");
        }

        return view;

    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addBody(List<ExchangeRecordModel.BodyBean> body) {
        list.addAll(body);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_exchangerecord_iv)
        ImageView itemExchangerecordIv;
        @Bind(R.id.item_exchangerecord_name_tx)
        TextView itemExchangerecordNameTx;
        @Bind(R.id.item_exchangerecord_integral_tx)
        TextView itemExchangerecordIntegralTx;
        @Bind(R.id.item_exchangerecord_num_tx)
        TextView itemExchangerecordNumTx;
        @Bind(R.id.item_exchangerecord_time_tx)
        TextView itemExchangerecordTimeTx;
        @Bind(R.id.item_exchangerecord_status_tx)
        TextView itemExchangerecordStatusTx;
        @Bind(R.id.item_exchangerecord_exchange_ll)
        LinearLayout itemExchangerecordExchangeLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
