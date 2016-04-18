package com.ipoxiao.idlenetwork.module.home.domain;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Administrator on 2016/1/12.
 */
public class GroupUser implements Parcelable {

    private String id;
    private String nickname;
    private String head;
    private String head_link;

    public GroupUser() {
    }

    protected GroupUser(Parcel in) {
        id = in.readString();
        nickname = in.readString();
        head = in.readString();
        head_link = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname.isEmpty()?"匿名":nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHead_link() {
        return head_link;
    }

    public void setHead_link(String head_link) {
        this.head_link = head_link;
    }


    public static final Creator<GroupUser> CREATOR = new Creator<GroupUser>() {
        @Override
        public GroupUser createFromParcel(Parcel in) {
            return new GroupUser(in);
        }

        @Override
        public GroupUser[] newArray(int size) {
            return new GroupUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nickname);
        dest.writeString(head);
        dest.writeString(head_link);
    }
}
