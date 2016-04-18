package com.ipoxiao.idlenetwork.module.home.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class ChatSessionGroup implements Serializable {

    private List<ChatSession> chatSessions;

    public List<ChatSession> getChatSessions() {
        return chatSessions;
    }

    public void setChatSessions(List<ChatSession> chatSessions) {
        this.chatSessions = chatSessions;
    }
}
