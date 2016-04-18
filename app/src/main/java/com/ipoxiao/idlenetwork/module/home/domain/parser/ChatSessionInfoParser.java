package com.ipoxiao.idlenetwork.module.home.domain.parser;

import com.alibaba.fastjson.JSON;
import com.ipoxiao.idlenetwork.framework.http.parser.BaseParser;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSessionInfo;
import com.ipoxiao.idlenetwork.module.home.domain.GroupUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public class ChatSessionInfoParser extends BaseParser<ChatSessionInfo> {

    @Override
    public List<ChatSessionInfo> parseArrayObject(String data, Class<ChatSessionInfo> clazz) {
        List<ChatSessionInfo> infos = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                String child = array.getString(i);
                ChatSessionInfo info = parseObject(child, ChatSessionInfo.class);
                infos.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return infos;
    }

    @Override
    public ChatSessionInfo parseObject(String data, Class<ChatSessionInfo> clazz) {
        ChatSessionInfo chatSession = JSON.parseObject(data, ChatSessionInfo.class);
        try {
            JSONObject object = new JSONObject(data);
            String user = object.getString("users");
            List<GroupUser> groupUsers = JSON.parseArray(user, GroupUser.class);
            if (groupUsers == null) {
                groupUsers = new ArrayList<>();
            }
            chatSession.setGroupUsers(groupUsers);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chatSession;
    }
}
