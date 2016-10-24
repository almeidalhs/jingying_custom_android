package com.atman.jixin.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.SortGroupFriendsAdapter;
import com.atman.jixin.model.response.GetLikeListModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.ShopIMActivity;
import com.atman.jixin.utils.Common;
import com.atman.jixin.widget.telephonebook.CharacterParser;
import com.atman.jixin.widget.telephonebook.FriendsPinyinComparator;
import com.atman.jixin.widget.telephonebook.PinyinComparator;
import com.atman.jixin.widget.telephonebook.SideBar;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/21.
 */

public class MyAttentionListActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.mofriend_listview)
    ListView mofriendListview;
    @Bind(R.id.mofriend_no_friends)
    TextView mofriendNoFriends;
    @Bind(R.id.mofriend_title_layout_catalog)
    TextView mofriendTitleLayoutCatalog;
    @Bind(R.id.mofriend_title_layout)
    LinearLayout mofriendTitleLayout;
    @Bind(R.id.mofriend_dialog)
    TextView mofriendDialog;
    @Bind(R.id.mofriend_sidrbar)
    SideBar mofriendSidrbar;

    private Context mContext = MyAttentionListActivity.this;
    private GetLikeListModel mGetLikeListModel;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<GetLikeListModel.BodyBean> SourceDateList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private FriendsPinyinComparator pinyinComparator;
    private SortGroupFriendsAdapter adapter;
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myattentionlist);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("我的关注");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_GetLike_List)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GETLIKE_LIST_ID).id(Common.NET_GETLIKE_LIST_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GETLIKE_LIST_ID) {
            mGetLikeListModel = mGson.fromJson(data, GetLikeListModel.class);

            if (mGetLikeListModel.getBody().size()>0) {
                mofriendTitleLayout.setVisibility(View.VISIBLE);
            } else {
                mofriendTitleLayout.setVisibility(View.GONE);
            }
            InitLieView();
        }
    }

    private void InitLieView() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new FriendsPinyinComparator();
        mofriendSidrbar.setTextView(mofriendDialog);

        // 设置右侧触摸监听
        mofriendSidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mofriendListview.setSelection(position);
                }

            }
        });

        SourceDateList = filledData(mGetLikeListModel.getBody());
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);

        adapter = new SortGroupFriendsAdapter(mContext, SourceDateList, this);
        mofriendListview.setAdapter(adapter);
        mofriendListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);

                if (firstVisibleItem==0) {
                    mofriendTitleLayout.setVisibility(View.GONE);
                } else {
                    mofriendTitleLayout.setVisibility(View.VISIBLE);
                }

                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mofriendTitleLayout
                            .getLayoutParams();
                    params.topMargin = 0;
                    mofriendTitleLayout.setLayoutParams(params);
                    if (SourceDateList.size()>0) {
                        mofriendTitleLayoutCatalog.setText(SourceDateList.get(
                                getPositionForSection(section)).getSortLetters());
                    } else {
                        mofriendTitleLayoutCatalog.setVisibility(View.GONE);
                    }
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = mofriendTitleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mofriendTitleLayout
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            mofriendTitleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                mofriendTitleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<GetLikeListModel.BodyBean> filledData(List<GetLikeListModel.BodyBean> date) {
        List<GetLikeListModel.BodyBean> mSortList = new ArrayList<>();

        for (int i = 0; i < date.size(); i++) {
            GetLikeListModel.BodyBean sortModel = new GetLikeListModel.BodyBean();
            sortModel.setId(date.get(i).getId());
            sortModel.setFavId(date.get(i).getFavId());
            sortModel.setFavTime(date.get(i).getFavTime());
            sortModel.setIntegral(date.get(i).getIntegral());
            sortModel.setStoreBanner(date.get(i).getStoreBanner());
            sortModel.setStoreName(date.get(i).getStoreName());
            sortModel.setSelect(false);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getStoreName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        try {
            return SourceDateList.get(position).getSortLetters().charAt(0);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < SourceDateList.size(); i++) {
            String sortStr = SourceDateList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GETLIKE_LIST_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(ShopIMActivity.buildIntent(mContext, adapter.getItem(position).getId()
                , adapter.getItem(position).getStoreName()));
    }
}
