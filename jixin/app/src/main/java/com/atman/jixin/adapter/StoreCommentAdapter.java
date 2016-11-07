package com.atman.jixin.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.StoreCommentModel;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.utils.face.SmileUtils;
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
 * 时间 16/7/12 13:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class StoreCommentAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<StoreCommentModel.BodyBean> dataList;
    private AdapterInterface mAdapterInterface;

    public StoreCommentAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
    }

    public void addBody(StoreCommentModel.BodyBean data) {
        this.dataList.add(0, data);
        notifyDataSetChanged();
    }

    public void addBody(List<StoreCommentModel.BodyBean> data) {
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public StoreCommentModel.BodyBean getItem(int position) {
        return dataList.get(position);
    }

    public void deleteItemById(int id) {
        dataList.remove(id);
        notifyDataSetChanged();
    }

    public void setLikeItemById(int id) {
        dataList.get(id).setIsLike(1);
        dataList.get(id).setLikeNum(dataList.get(id).getLikeNum()+1);
        notifyDataSetChanged();
    }

    public void setNotLikeItemById(int id) {
        dataList.get(id).setIsLike(0);
        if (dataList.get(id).getLikeNum()-1<=0) {
            dataList.get(id).setLikeNum(0);
        } else {
            dataList.get(id).setLikeNum(dataList.get(id).getLikeNum()-1);
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_comment_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StoreCommentModel.BodyBean temp = dataList.get(position);

        String url = temp.getUserAvatar();
        if (!url.startsWith("http")) {
            url = Common.ImageUrl + temp.getUserAvatar();
        }
        ImageLoader.getInstance().displayImage(url, holder.itemCommentHeadIv
                , MyBaseApplication.getApplication().optionsHead);
        if (temp.getStoreId()==0) {
            holder.itemCommentNameTx.setText(temp.getUserName());
        } else {
            holder.itemCommentNameTx.setText(Html.fromHtml("<font color=\"#10ccbe\">[商家]</font>" + temp.getUserName()));
        }
        holder.itemCommentTimeTx.setText(MyTools.convertTimeS(temp.getAddTime()*1000));
        holder.itemCommentLikeTx.setText(temp.getLikeNum() + "  ");
        Drawable drawable = null;
        if (temp.getIsLike() == 1) {
            drawable = context.getResources().getDrawable(R.mipmap.item_comment_like);
        } else {
            drawable = context.getResources().getDrawable(R.mipmap.item_comment_not_like);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth() * 2 / 3, drawable.getMinimumHeight() * 2 / 3);
        holder.itemCommentLikeTx.setCompoundDrawables(null, null, drawable, null);
        holder.itemCommentContentTx.setText(SmileUtils.getEmotionContent(context
                , holder.itemCommentContentTx, temp.getContent()));

        if (position==dataList.size()-1) {
            holder.itemCommentLineLl.setVisibility(View.GONE);
        } else {
            holder.itemCommentLineLl.setVisibility(View.VISIBLE);
        }

        holder.itemCommentLikeTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });
        holder.itemCommentHeadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return convertView;
    }

    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_comment_head_iv)
        ShapeImageView itemCommentHeadIv;
        @Bind(R.id.item_comment_name_tx)
        TextView itemCommentNameTx;
        @Bind(R.id.item_comment_time_tx)
        TextView itemCommentTimeTx;
        @Bind(R.id.item_comment_like_tx)
        TextView itemCommentLikeTx;
        @Bind(R.id.item_comment_content_tx)
        TextView itemCommentContentTx;
        @Bind(R.id.item_comment_line_ll)
        LinearLayout itemCommentLineLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
