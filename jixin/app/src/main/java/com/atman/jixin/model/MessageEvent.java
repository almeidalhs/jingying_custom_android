package com.atman.jixin.model;

import com.atman.jixin.model.response.GetMessageModel;

/**
 * Created by tangbingliang on 16/10/28.
 */

public class MessageEvent {
    public GetMessageModel mGetMessageModel;

    public MessageEvent (GetMessageModel mGetMessageModel) {
        this.mGetMessageModel = mGetMessageModel;
    }
}
