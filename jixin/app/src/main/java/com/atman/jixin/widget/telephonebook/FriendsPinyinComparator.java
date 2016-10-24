package com.atman.jixin.widget.telephonebook;

import com.atman.jixin.model.response.GetLikeListModel;

import java.util.Comparator;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/26 09:42
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class FriendsPinyinComparator implements Comparator<GetLikeListModel.BodyBean> {

    public int compare(GetLikeListModel.BodyBean o1, GetLikeListModel.BodyBean o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
