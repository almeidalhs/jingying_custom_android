package com.atman.jixin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atman.jixin.model.response.GoodsClassModel;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/4/25 15:23
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private LayoutInflater mInflater;
    private List<GoodsClassModel.BodyBean> list;
    private List<Fragment> fragments;

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<GoodsClassModel.BodyBean> list) {
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }
    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment=null;
        try {
            fragment=(Fragment)super.instantiateItem(container,position);
        }catch (Exception e){

        }
        return fragment;
    }

    @Override
    public void destroyItem(View Groupcontainer, int position, Object object) {

    }
    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position %list.size()).getStcName();
    }
    public List<Fragment> getFragments(){
        return fragments;
    }
    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }
}