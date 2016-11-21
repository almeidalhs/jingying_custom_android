package com.atman.jixin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.MyConsumptionRecordModel;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.widget.XCFlowLayout;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
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
public class MyConsumptionRecordAdapter extends BaseAdapter {

    private List<MyConsumptionRecordModel.BodyBean> list = null;
    private Context mContext;
    private AdapterInterface onItemClick;
    private ViewGroup.MarginLayoutParams lp;

    public MyConsumptionRecordAdapter(Context mContext, AdapterInterface onItemClick) {
        this.mContext = mContext;
        this.onItemClick = onItemClick;
        this.list = new ArrayList<>();
        lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = DensityUtil.dp2px(mContext, 2);
        lp.rightMargin = DensityUtil.dp2px(mContext, 2);
        lp.topMargin = DensityUtil.dp2px(mContext, 0);
        lp.bottomMargin = DensityUtil.dp2px(mContext, 0);
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<MyConsumptionRecordModel.BodyBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return list.size();
    }

    public MyConsumptionRecordModel.BodyBean getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final MyConsumptionRecordModel.BodyBean mData = list.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_myconsumes_view, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.itemMyconsumesNumTx.setText(mData.getTitle());
        viewHolder.itemMyconsumesShopnameTx.setText(mData.getStoreName());
        viewHolder.itemMyconsumesTimeTv.setText(MyTools.convertTime(mData.getAddTime() * 1000, "yyyy-MM-dd HH:mm"));
        viewHolder.itemMyconsumesTotalTv.setText("共" + mData.getGoodsBeanList().size() + "件商品,合计￥ " + mData.getTotal());

        viewHolder.itemMyconsumesGoodsLl.removeAllViews();
        for (int i = 0; i < mData.getGoodsBeanList().size(); i++) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_part_myconsumes_view, null);
            viewHolder.itemMyconsumesGoodsLl.addView(v);
            ImageView itemMyconsumesIv = (ImageView) v.findViewById(R.id.item_myconsumes_iv);
            TextView itemMyconsumesGoodsnameTv = (TextView) v.findViewById(R.id.item_myconsumes_goodsname_tv);
            TextView itemMyconsumesGoodsdescriptionTv = (TextView) v.findViewById(R.id.item_myconsumes_goodsdescription_tv);
            TextView itemMyconsumesGoodsnumTv = (TextView) v.findViewById(R.id.item_myconsumes_goodsnum_tv);
            TextView itemMyconsumesGoodspriceTv = (TextView) v.findViewById(R.id.item_myconsumes_goodsprice_tv);

            ImageLoader.getInstance().displayImage(Common.ImageUrl + mData.getGoodsBeanList().get(i).getGoodsImage()
                    , itemMyconsumesIv, MyBaseApplication.getApplication().optionsNot);
            itemMyconsumesGoodsnameTv.setText(mData.getGoodsBeanList().get(i).getGoodsName());
            if (mData.getGoodsBeanList().get(i).getGoodsInfo() == null
                    || mData.getGoodsBeanList().get(i).getGoodsInfo().isEmpty()) {
                itemMyconsumesGoodsdescriptionTv.setText("暂无添加说明");
            } else {
                itemMyconsumesGoodsdescriptionTv.setText(mData.getGoodsBeanList().get(i).getGoodsInfo());
            }
            itemMyconsumesGoodsnumTv.setText("数量:" + mData.getGoodsBeanList().get(i).getGoodsCount());
            itemMyconsumesGoodspriceTv.setText("￥ " + mData.getGoodsBeanList().get(i).getGoodsPrice());
        }

        viewHolder.itemMemberrecordFlowlayout.removeAllViews();
        if (mData.getMansongRuleList().size() > 0 || mData.getCouponList().size() > 0) {
            viewHolder.itemMyconsumesOffersLl.setVisibility(View.VISIBLE);
        } else {
            viewHolder.itemMyconsumesOffersLl.setVisibility(View.GONE);
        }
        for (int i = 0; i < mData.getMansongRuleList().size(); i++) {
            TextView tv = new TextView(mContext);
            tv.setText("全场满"+mData.getMansongRuleList().get(i).getPrice()
                    +"减"+mData.getMansongRuleList().get(i).getDiscount());
            tv.setTextColor(Color.WHITE);
            tv.setPadding(DensityUtil.dp2px(mContext, 5), DensityUtil.dp2px(mContext, 1),
                    DensityUtil.dp2px(mContext, 5), DensityUtil.dp2px(mContext, 1));
            tv.setTextSize(10);
            tv.setBackgroundColor(mContext.getResources().getColor(R.color.color_ec7b6b));
            viewHolder.itemMemberrecordFlowlayout.addView(tv, lp);
        }

        for (int i=0;i<mData.getCouponList().size();i++) {
            TextView tv = new TextView(mContext);
            tv.setText("满"+mData.getCouponList().get(i).getCouponLimit()
                    +"减"+mData.getCouponList().get(i).getCouponPrice()+"优惠券");
            tv.setTextColor(Color.WHITE);
            tv.setPadding(DensityUtil.dp2px(mContext, 5), DensityUtil.dp2px(mContext, 1),
                    DensityUtil.dp2px(mContext, 5), DensityUtil.dp2px(mContext, 1));
            tv.setTextSize(10);
            tv.setBackgroundColor(mContext.getResources().getColor(R.color.color_ec7b6b));
            viewHolder.itemMemberrecordFlowlayout.addView(tv, lp);
        }

        viewHolder.itemMyconsumesShopnameTx.setOnClickListener(new View.OnClickListener() {
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

    public void addBody(List<MyConsumptionRecordModel.BodyBean> body) {
        list.addAll(body);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_myconsumes_num_tx)
        TextView itemMyconsumesNumTx;
        @Bind(R.id.item_myconsumes_shopname_tx)
        TextView itemMyconsumesShopnameTx;
        @Bind(R.id.item_myconsumes_goods_ll)
        LinearLayout itemMyconsumesGoodsLl;
        @Bind(R.id.item_memberrecord_flowlayout)
        XCFlowLayout itemMemberrecordFlowlayout;
        @Bind(R.id.item_myconsumes_offers_ll)
        LinearLayout itemMyconsumesOffersLl;
        @Bind(R.id.item_myconsumes_time_tv)
        TextView itemMyconsumesTimeTv;
        @Bind(R.id.item_myconsumes_total_tv)
        TextView itemMyconsumesTotalTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
