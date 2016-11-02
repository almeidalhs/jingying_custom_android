package com.atman.jixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.ExchangeRecordModel;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.widget.SlidingButtonView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/5/17 17:31
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ExchangeRecordAllAdapter extends RecyclerView.Adapter<ExchangeRecordAllAdapter.MyViewHolder>
        implements SlidingButtonView.IonSlidingButtonListener {

    private Context mContext;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private List<ExchangeRecordModel.BodyBean> mAllList;
    private SlidingButtonView mMenu = null;
    private int width;

    public ExchangeRecordAllAdapter(Context context, int width, IonSlidingViewClickListener mListener) {
        this.mContext = context;
        this.width = width;
        this.mIDeleteBtnClickListener = mListener;
        mAllList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mAllList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final ExchangeRecordModel.BodyBean mData = mAllList.get(position);

        ImageLoader.getInstance().displayImage(Common.ImageUrl + mData.getGoodsImage()
                , holder.itemExchangerecordIv, MyBaseApplication.getApplication().optionsHead);
        holder.itemExchangerecordNameTx.setText(mData.getGoodsName());
        holder.itemExchangerecordIntegralTx.setText("积分: " + mData.getIntegral());
        holder.itemExchangerecordNumTx.setText("兑换数量:" + mData.getAmount());
        holder.itemExchangerecordTimeTx.setText(MyTools.convertTime(mData.getAddTime() * 1000, "yyyy-MM-dd HH:mm"));
        //0-取消兑换 1-待处理 2-兑换成功
        if (mData.getState() == 1) {
            holder.itemExchangerecordStatusTx.setText("处理中");
            holder.itemExchangerecordStatusTx.setTextColor(mContext.getResources().getColor(R.color.color_62b3ee));
        } else if (mData.getState() == 0) {
            holder.itemExchangerecordStatusTx.setText("已取消");
            holder.itemExchangerecordStatusTx.setTextColor(mContext.getResources().getColor(R.color.color_df2138));
        } else if (mData.getState() == 2) {
            holder.itemExchangerecordStatusTx.setText("兑换成功");
            holder.itemExchangerecordStatusTx.setTextColor(mContext.getResources().getColor(R.color.color_757575));
        }

        if (mData.getStoreName()!=null) {
            holder.itemExchangerecordShopTx.setText("[商家]"+mData.getStoreName());
            holder.itemExchangerecordShopTx.setVisibility(View.VISIBLE);
        } else {
            holder.itemExchangerecordShopTx.setVisibility(View.GONE);
        }

        holder.item_fullcut_root_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }

            }
        });

        holder.itemExchangerecordExchangeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }

            }
        });

        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });

        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = width;
        holder.itemLineIv.getLayoutParams().width = width;

        holder.mSlidingButtonView.changeWidth(1, 80);

        holder.mSlidingButtonView.closeMenu();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_exchangerecordall_view, arg0,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView btn_Delete;
        public ViewGroup layout_content;
        public RelativeLayout item_fullcut_root_rl;
        public SlidingButtonView mSlidingButtonView;

        public ImageView itemExchangerecordIv;
        public ImageView itemLineIv;
        public TextView itemExchangerecordNameTx;
        public TextView itemExchangerecordIntegralTx;
        public TextView itemExchangerecordNumTx;
        public TextView itemExchangerecordTimeTx;
        public TextView itemExchangerecordShopTx;
        public TextView itemExchangerecordStatusTx;
        public LinearLayout itemExchangerecordExchangeLl;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            item_fullcut_root_rl = (RelativeLayout) itemView.findViewById(R.id.item_fullcut_root_rl);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);

            itemLineIv = (ImageView) itemView.findViewById(R.id.item_line_iv);
            itemExchangerecordIv = (ImageView) itemView.findViewById(R.id.item_exchangerecord_iv);
            itemExchangerecordNameTx = (TextView) itemView.findViewById(R.id.item_exchangerecord_name_tx);
            itemExchangerecordIntegralTx = (TextView) itemView.findViewById(R.id.item_exchangerecord_integral_tx);
            itemExchangerecordNumTx = (TextView) itemView.findViewById(R.id.item_exchangerecord_num_tx);
            itemExchangerecordTimeTx = (TextView) itemView.findViewById(R.id.item_exchangerecord_time_tx);
            itemExchangerecordShopTx = (TextView) itemView.findViewById(R.id.item_exchangerecord_shop_tx);
            itemExchangerecordStatusTx = (TextView) itemView.findViewById(R.id.item_exchangerecord_status_tx);
            itemExchangerecordExchangeLl = (LinearLayout) itemView.findViewById(R.id.item_exchangerecord_exchange_ll);

            mSlidingButtonView = (SlidingButtonView) itemView;
            mSlidingButtonView.setSlidingButtonListener(ExchangeRecordAllAdapter.this);
        }
    }

    public void addData(List<ExchangeRecordModel.BodyBean> mList) {
        mAllList.addAll(mList);
        notifyDataSetChanged();
    }

    public ExchangeRecordModel.BodyBean getItemById(int n) {
        return mAllList.get(n);
    }

    public void removeData(int position){
        mAllList.remove(position);
        notifyItemRemoved(position);
    }

    public void clearData(){
        mAllList.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        return false;
    }



    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
    }
}

