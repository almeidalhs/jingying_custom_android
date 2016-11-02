package com.atman.jixin.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.GetCompanyIntrodutionModel;
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
 * 时间 16/7/12 13:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class CompanyIntroductionAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetCompanyIntrodutionModel.BodyBean.FileListBean> dataList;
    private AdapterInterface mAdapterInterface;
    private AdapterAnimInter mAdapterAnimInter;

    public CompanyIntroductionAdapter(Context context, AdapterInterface mAdapterInterface
            , AdapterAnimInter mAdapterAnimInter) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        this.mAdapterAnimInter = mAdapterAnimInter;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
    }

    public void addBody(GetCompanyIntrodutionModel.BodyBean.FileListBean data) {
        this.dataList.add(data);
        notifyDataSetChanged();
    }

    public void addBody(List<GetCompanyIntrodutionModel.BodyBean.FileListBean> data) {
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public GetCompanyIntrodutionModel.BodyBean.FileListBean getItem(int position) {
        return dataList.get(position);
    }

    public void deleteItemById(int id) {
        dataList.remove(id);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_companyintroduction_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetCompanyIntrodutionModel.BodyBean.FileListBean temp = dataList.get(position);

        holder.itemCompanyVideoRl.setVisibility(View.GONE);
        holder.itemCompanyPictrueIv.setVisibility(View.GONE);
        holder.itemCompanyAudioRl.setVisibility(View.GONE);

        String Url = "";

        holder.itemCompanyIntroductionTx.setText(temp.getRemark());
        if (temp.getType() == 3) {//视频
            Url = temp.getShowImg();
            if (!Url.startsWith("http")) {
                Url = Common.ImageUrl + temp.getShowImg();
            }
            holder.itemCompanyVideoRl.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Url, holder.itemCompanyVideoIv
                    , MyBaseApplication.getApplication().optionsNot);
        } else if (temp.getType() == 2) {//语音
            holder.itemCompanyAudioRl.setVisibility(View.VISIBLE);
            holder.itemCompanyAudioTv.setText(MyTools.secToTime(Integer.parseInt(temp.getLength())));
        } else if (temp.getType() == 1) {//图片
            Url = temp.getFileUrl();
            if (!Url.startsWith("http")) {
                Url = Common.ImageUrl + temp.getFileUrl();
            }
            holder.itemCompanyPictrueIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Url, holder.itemCompanyPictrueIv
                    , MyBaseApplication.getApplication().optionsNot);
        }

        holder.itemCompanyVideoStartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        holder.itemCompanyAudioStartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterAnimInter.onItemAudio(v, position, (AnimationDrawable) holder
                        .itemCompanyAudioIv.getDrawable(), holder.itemCompanyAudioStartIv);
            }
        });

        holder.itemCompanyPictrueIv.setOnClickListener(new View.OnClickListener() {
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

    public interface AdapterAnimInter {
        void onItemAudio(View v, int position, AnimationDrawable animationDrawable, ImageView mImageView);
    }

    static class ViewHolder {
        @Bind(R.id.item_company_video_iv)
        ImageView itemCompanyVideoIv;
        @Bind(R.id.item_company_video_start_iv)
        ImageView itemCompanyVideoStartIv;
        @Bind(R.id.item_company_video_rl)
        RelativeLayout itemCompanyVideoRl;
        @Bind(R.id.item_company_audio_iv)
        ImageView itemCompanyAudioIv;
        @Bind(R.id.item_company_audio_start_iv)
        ImageView itemCompanyAudioStartIv;
        @Bind(R.id.item_company_audio_tv)
        TextView itemCompanyAudioTv;
        @Bind(R.id.item_company_audio_rl)
        RelativeLayout itemCompanyAudioRl;
        @Bind(R.id.item_company_pictrue_iv)
        ImageView itemCompanyPictrueIv;
        @Bind(R.id.item_company_introduction_tx)
        TextView itemCompanyIntroductionTx;
        @Bind(R.id.item_company_content_ll)
        LinearLayout itemCompanyContentLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
