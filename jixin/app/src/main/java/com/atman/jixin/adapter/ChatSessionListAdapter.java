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
import com.atman.jixin.model.bean.ChatListModel;
import com.atman.jixin.model.iimp.ADChatTargetType;
import com.atman.jixin.model.iimp.ADChatType;
import com.atman.jixin.model.response.ExchangeRecordModel;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.utils.face.SmileUtils;
import com.atman.jixin.widget.SlidingButtonView;
import com.base.baselibs.widget.ShapeImageView;
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
public class ChatSessionListAdapter extends RecyclerView.Adapter<ChatSessionListAdapter.MyViewHolder>
        implements SlidingButtonView.IonSlidingButtonListener {

    private Context mContext;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private List<ChatListModel> mAllList;
    private SlidingButtonView mMenu = null;
    private int width;

    public ChatSessionListAdapter(Context context, int width, IonSlidingViewClickListener mListener) {
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

        final ChatListModel mData = mAllList.get(position);

        if (mData.getTargetType() == ADChatTargetType.ADChatTargetType_Shop) {
            holder.itemSessionIsstoreTx.setVisibility(View.VISIBLE);
            holder.mSlidingButtonView.changeWidth(2, 65);
            holder.memberLl.setVisibility(View.VISIBLE);
        } else {
            holder.itemSessionIsstoreTx.setVisibility(View.GONE);
            holder.mSlidingButtonView.changeWidth(1, 65);
            holder.memberLl.setVisibility(View.GONE);
        }

        if (mData.getTargetAvatar() != null) {
            if (mData.getTargetAvatar().isEmpty()) {
                holder.itemSessionHeadIv.setBackgroundResource(R.mipmap.img_sign_logo);
            } else {
                String url = "";
                if (mData.getTargetAvatar().toString().contains("http")) {
                    url = mData.getTargetAvatar();
                } else {
                    url = Common.ImageUrl + mData.getTargetAvatar();
                }
                ImageLoader.getInstance().displayImage(url, holder.itemSessionHeadIv
                        , MyBaseApplication.getApplication().optionsHead);
            }
        } else {
            holder.itemSessionHeadIv.setBackgroundResource(R.mipmap.img_sign_logo);
        }

        holder.itemSessionNickTx.setText(mData.getTargetName());
        holder.itemSessionTimeTx.setText(MyTools.convertTimeS(mData.getSendTime()));

        if (mData.getUnreadNum() > 0) {
            holder.itemSessionUnreadTx.setVisibility(View.VISIBLE);
            if (mData.getUnreadNum() > 99) {
                holder.itemSessionUnreadTx.setText("99+");
            } else {
                holder.itemSessionUnreadTx.setText(mData.getUnreadNum() + "");
            }
        } else {
            holder.itemSessionUnreadTx.setVisibility(View.INVISIBLE);
        }

        String content = "";
        if (mData.getType() == ADChatType.ADChatType_Text) {
            content = mData.getContent();
        } else if (mData.getType() == ADChatType.ADChatType_Image) {
            content = "[图片]";
        } else if (mData.getType() == ADChatType.ADChatType_Audio) {
            content = "[语音]";
        } else if (mData.getType() == ADChatType.ADChatType_Video) {
            content = "[视频]";
        } else if (mData.getType() == ADChatType.ADChatType_ImageText) {
            content = mData.getContent();
        } else {
            content = mData.getContent();
        }
        if (content != null) {
            holder.itemSessionContentTx.setText(SmileUtils.getEmotionContent(mContext
                    , holder.itemSessionContentTx, content));
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

        holder.deleteLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });

        holder.memberLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();//关闭菜单
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });

        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = width;
        holder.itemLineIv.getLayoutParams().width = width;

        holder.mSlidingButtonView.closeMenu();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chatsession_view, arg0,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView btn_Delete;
        public TextView btn_Member;
        public ViewGroup layout_content;
        public LinearLayout deleteLl;
        public LinearLayout memberLl;
        public RelativeLayout item_fullcut_root_rl;
        public SlidingButtonView mSlidingButtonView;

        public ImageView itemLineIv;
        public ShapeImageView itemSessionHeadIv;
        public TextView itemSessionUnreadTx;
        public TextView itemSessionIsstoreTx;
        public TextView itemSessionNickTx;
        public TextView itemSessionTimeTx;
        public TextView itemSessionContentTx;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            btn_Member = (TextView) itemView.findViewById(R.id.tv_member);
            item_fullcut_root_rl = (RelativeLayout) itemView.findViewById(R.id.item_fullcut_root_rl);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);
            deleteLl = (LinearLayout) itemView.findViewById(R.id.delete_ll);
            memberLl = (LinearLayout) itemView.findViewById(R.id.member_ll);

            itemLineIv = (ImageView) itemView.findViewById(R.id.item_line_iv);
            itemSessionHeadIv = (ShapeImageView) itemView.findViewById(R.id.item_session_head_iv);
            itemSessionUnreadTx = (TextView) itemView.findViewById(R.id.item_session_unread_tx);
            itemSessionIsstoreTx = (TextView) itemView.findViewById(R.id.item_session_isstore_tx);
            itemSessionNickTx = (TextView) itemView.findViewById(R.id.item_session_nick_tx);
            itemSessionTimeTx = (TextView) itemView.findViewById(R.id.item_session_time_tx);
            itemSessionContentTx = (TextView) itemView.findViewById(R.id.item_session_content_tx);

            mSlidingButtonView = (SlidingButtonView) itemView;
            mSlidingButtonView.setSlidingButtonListener(ChatSessionListAdapter.this);
        }
    }

    public void addData(List<ChatListModel> mList) {
        mAllList.addAll(mList);
        notifyDataSetChanged();
    }

    public void clearUnreadNum(int p) {
        this.mAllList.get(p).setUnreadNum(0);
        notifyDataSetChanged();
    }

    public ChatListModel getItem(int n) {
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

