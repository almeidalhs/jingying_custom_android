package com.atman.jixin.model;

import com.atman.jixin.model.bean.ChatMessageModel;
import com.atman.jixin.model.response.GetMessageModel;

/**
 * Created by tangbingliang on 16/10/28.
 */

public class MessageEvent {
    public GetMessageModel mGetMessageModel;
    public ChatMessageModel mChatMessageModel;

    public MessageEvent (GetMessageModel mGetMessageModel, ChatMessageModel mChatMessageModel) {
        this.mGetMessageModel = mGetMessageModel;
        this.mChatMessageModel = mChatMessageModel;
    }
}
