package com.ipoxiao.idlenetwork.module.common.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/22.
 */
public class Version implements Serializable {

    private String id;
    private String version;
    private String is_force;
    private String create_time;
    private String files;
    private String description;
    private String files_link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIs_force() {
        return is_force;
    }

    public void setIs_force(String is_force) {
        this.is_force = is_force;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFiles_link() {
        return files_link;
    }

    public void setFiles_link(String files_link) {
        this.files_link = files_link;
    }
}
