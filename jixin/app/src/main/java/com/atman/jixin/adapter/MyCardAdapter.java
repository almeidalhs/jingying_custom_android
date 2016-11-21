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
import com.atman.jixin.model.response.MyCardModel;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.widget.XCFlowLayout;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;

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
public class MyCardAdapter extends BaseAdapter {

    private List<MyCardModel.BodyBean> list = null;
    private Context mContext;
    private AdapterInterface onItemClick;
    private ViewGroup.MarginLayoutParams lp;

    public MyCardAdapter(Context mContext, AdapterInterface onItemClick) {
        this.mContext = mContext;
        this.onItemClick = onItemClick;
        this.list = new ArrayList<>();
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<MyCardModel.BodyBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return list.size();
    }

    public MyCardModel.BodyBean getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final MyCardModel.BodyBean mData = list.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_mycard_view, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.itemMycardPriceTx.setText(mData.getCouponPrice()+"");
        viewHolder.itemMycardLimitTx.setText("满"+mData.getCouponLimit()+"可用");
        viewHolder.itemMycardNameTx.setText("[商家]"+mData.getStoreName());
        viewHolder.itemMycardStarttimeTx.setText("开始:"+ MyTools.convertTime(mData.getCouponStartDate()*1000, "yyyy-MM-dd HH:mm"));
        viewHolder.itemMycardEndtimeTx.setText("结束:"+MyTools.convertTime(mData.getCouponEndDate()*1000, "yyyy-MM-dd HH:mm"));

        return view;

    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addBody(List<MyCardModel.BodyBean> body) {
        list.addAll(body);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_mycard_price_tx)
        TextView itemMycardPriceTx;
        @Bind(R.id.item_mycard_limit_tx)
        TextView itemMycardLimitTx;
        @Bind(R.id.item_mycard_name_tx)
        TextView itemMycardNameTx;
        @Bind(R.id.item_mycard_starttime_tx)
        TextView itemMycardStarttimeTx;
        @Bind(R.id.item_mycard_endtime_tx)
        TextView itemMycardEndtimeTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
