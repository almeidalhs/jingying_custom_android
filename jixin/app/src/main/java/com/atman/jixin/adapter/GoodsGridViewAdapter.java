package com.atman.jixin.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.GetGoodsByClassIdModel;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/4/25 17:59
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GoodsGridViewAdapter extends BaseAdapter {

    private List<GetGoodsByClassIdModel.BodyBean> body;
    private Context mContext;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private ImageLoader mImageLoader;
    private AdapterInterface mOnBack;
    private DecimalFormat df;
    private LinearLayout.LayoutParams params;
    private LinearLayout.LayoutParams paramsLl;

    public GoodsGridViewAdapter(Context mContext, int wight, AdapterInterface mOnBack) {
        this.body = new ArrayList<>();
        this.mContext = mContext;
        this.mImageLoader = ImageLoader.getInstance();
        this.mOnBack = mOnBack;
        layoutInflater = LayoutInflater.from(mContext);
        df = new DecimalFormat("##0.00");
        int w = (wight - DensityUtil.dp2px(mContext,15))/2;
        params = new LinearLayout.LayoutParams(w, w);
        paramsLl = new LinearLayout.LayoutParams(w, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setBody(List<GetGoodsByClassIdModel.BodyBean> body) {
        this.body.addAll(body);
        this.notifyDataSetChanged();
    }

    public void clearBody(){
        this.body.clear();
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public GetGoodsByClassIdModel.BodyBean getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_goodsgridview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetGoodsByClassIdModel.BodyBean mBodyEntity = body.get(position);

        String url = mBodyEntity.getGoodsImage();
        if (!url.startsWith("http")) {
            url =  Common.ImageUrl + mBodyEntity.getGoodsImage();
        }
        ImageLoader.getInstance().displayImage(url,
                holder.itemGoodspreviewIv, MyBaseApplication.getApplication().optionsHead);
        holder.itemGoodspreviewIv.setLayoutParams(params);
        holder.itemGoodspreviewLl.setLayoutParams(paramsLl);
        holder.itemGoodspreviewNameTx.setText(mBodyEntity.getGoodsName());
        holder.itemGoodspreviewPriceTx.setText("¥ " + mBodyEntity.getPrice());
        holder.itemGoodspreviewLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnBack.onItemClick(v, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_goodspreview_iv)
        ImageView itemGoodspreviewIv;
        @Bind(R.id.item_goodspreview_name_tx)
        TextView itemGoodspreviewNameTx;
        @Bind(R.id.item_goodspreview_price_tx)
        TextView itemGoodspreviewPriceTx;
        @Bind(R.id.item_goodspreview_collection_tx)
        TextView itemGoodspreviewCollectionTx;
        @Bind(R.id.item_goodspreview_ll)
        LinearLayout itemGoodspreviewLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
