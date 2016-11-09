package com.atman.jixin.model;

/**
 * Created by tangbingliang on 16/11/9.
 */

public class updateChatMessageServiceEvent {

    public int i;
    public String str;
    public long chatId;
    public long Id;

    public updateChatMessageServiceEvent(int i, String str, long chatId, long Id) {
        this.i = i;
        this.str = str;
        this.chatId = chatId;
        this.Id = Id;
    }
}
