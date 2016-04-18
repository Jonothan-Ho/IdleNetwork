package com.ipoxiao.idlenetwork.module.home.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public class ChatSessionInfo implements Serializable {
    private String id;
    private String title;
    private String cid;
    private String uid;
    private List<GroupUser> groupUsers;
    private String category_title;
    private String cityindex;
    private String cityindex_link;
    private String dist;
    private String imgpic;
    private String imgpic_link;
    private String create_time;
    private int is_join;
    private String group_id;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getIs_join() {
        return is_join;
    }

    public void setIs_join(int is_join) {
        this.is_join = is_join;
    }

    public String getCityindex_link() {
        return cityindex_link;
    }

    public void setCityindex_link(String cityindex_link) {
        this.cityindex_link = cityindex_link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCityindex() {
        return cityindex;
    }

    public void setCityindex(String cityindex) {
        this.cityindex = cityindex;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public List<GroupUser> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<GroupUser> groupUsers) {
        this.groupUsers = groupUsers;
    }

    public String getImgpic() {
        return imgpic;
    }

    public void setImgpic(String imgpic) {
        this.imgpic = imgpic;
    }

    public String getImgpic_link() {
        return imgpic_link;
    }

    public void setImgpic_link(String imgpic_link) {
        this.imgpic_link = imgpic_link;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
