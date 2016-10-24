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
import com.atman.jixin.model.response.IntegralGoodsModel;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.widget.ShapeImageView;
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
public class IntegralExchangeAdapter extends BaseAdapter {

    private List<IntegralGoodsModel.BodyBean> list = null;
    private Context mContext;
    private AdapterInterface onItemClick;

    public IntegralExchangeAdapter(Context mContext, AdapterInterface onItemClick) {
        this.mContext = mContext;
        this.onItemClick = onItemClick;
        this.list = new ArrayList<>();
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<IntegralGoodsModel.BodyBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return list.size();
    }

    public IntegralGoodsModel.BodyBean getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final IntegralGoodsModel.BodyBean mData = list.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_integralgoods_view, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance().displayImage(Common.ImageUrl+mData.getGoodsImage()
                , viewHolder.itemIntegralgoodsIv, MyBaseApplication.getApplication().optionsHead);
        viewHolder.itemIntegralgoodsNameTx.setText(mData.getGoodsName());
        viewHolder.itemIntegralgoodsExchangeTx.setText(mData.getIntegral()+"");
        viewHolder.itemIntegralgoodsNumTx.setText("积分: "+mData.getIntegral());
        viewHolder.itemIntegralgoodsPriceTx.setText("售价:￥ "+mData.getGoodsPrice());
        viewHolder.itemIntegralgoodsStockTx.setText("剩余库存: "+mData.getStoreLimit());
        viewHolder.itemIntegralgoodsLimitTx.setText("每人限兑: "+mData.getUserLimit());

        viewHolder.itemIntegralgoodsExchangeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(v, position);
            }
        });

        return view;

    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addBody(List<IntegralGoodsModel.BodyBean> body) {
        list.addAll(body);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_integralgoods_iv)
        ImageView itemIntegralgoodsIv;
        @Bind(R.id.item_integralgoods_name_tx)
        TextView itemIntegralgoodsNameTx;
        @Bind(R.id.item_integralgoods_num_tx)
        TextView itemIntegralgoodsNumTx;
        @Bind(R.id.item_integralgoods_price_tx)
        TextView itemIntegralgoodsPriceTx;
        @Bind(R.id.item_integralgoods_stock_tx)
        TextView itemIntegralgoodsStockTx;
        @Bind(R.id.item_integralgoods_limit_tx)
        TextView itemIntegralgoodsLimitTx;
        @Bind(R.id.item_integralgoods_exchange_tx)
        TextView itemIntegralgoodsExchangeTx;
        @Bind(R.id.item_integralgoods_exchange_ll)
        LinearLayout itemIntegralgoodsExchangeLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
