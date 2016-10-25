package com.atman.jixin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.MemberCenterModel;
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
public class MemberCenterAdapter extends BaseAdapter {

    private List<MemberCenterModel.BodyBean.UserListBean> list = null;
    private Context mContext;
    private AdapterInterface onItemClick;

    public MemberCenterAdapter(Context mContext, AdapterInterface onItemClick) {
        this.mContext = mContext;
        this.onItemClick = onItemClick;
        this.list = new ArrayList<>();
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<MemberCenterModel.BodyBean.UserListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return list.size();
    }

    public MemberCenterModel.BodyBean.UserListBean getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final MemberCenterModel.BodyBean.UserListBean mData = list.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_member_listview, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.itemMemberNameTv.setText(mData.getMemberName());
        viewHolder.itemMemberAgeTv.setText(mData.getMemberAge());
        viewHolder.itemMemberGanderTv.setText(mData.getMemberSex());
        if (mData.getMemberSign().isEmpty()) {
            viewHolder.itemMemberSignTv.setText("这家伙很忙,还未来得及设置签名!");
        } else {
            viewHolder.itemMemberSignTv.setText(mData.getMemberSign());
        }
        ImageLoader.getInstance().displayImage(Common.ImageUrl+mData.getMemberAvatar()
                , viewHolder.itemMemberHeadIv, MyBaseApplication.getApplication().optionsHead);

        String str = mData.getAddressDetail();
        if (!str.isEmpty()) {
            str += "/" + mData.getBindTime();
        } else {
            str = mData.getBindTime();
        }
        viewHolder.itemOtherTx.setText(str);

        viewHolder.itemMemberRootLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(v, position);
            }
        });

        return view;

    }

    static class ViewHolder {
        @Bind(R.id.item_member_head_iv)
        ShapeImageView itemMemberHeadIv;
        @Bind(R.id.item_member_name_tv)
        TextView itemMemberNameTv;
        @Bind(R.id.item_member_age_tv)
        TextView itemMemberAgeTv;
        @Bind(R.id.item_member_gander_tv)
        TextView itemMemberGanderTv;
        @Bind(R.id.item_member_sign_tv)
        TextView itemMemberSignTv;
        @Bind(R.id.item_other_tx)
        TextView itemOtherTx;
        @Bind(R.id.item_member_root_ll)
        LinearLayout itemMemberRootLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
