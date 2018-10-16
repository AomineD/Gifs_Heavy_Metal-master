package com.heavymetal.giphy.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hsn on 16/01/2018.
 */


public class Comment {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("trusted")
    @Expose
    private Boolean trusted;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
    }

    public Boolean getTrusted() {
        return trusted;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getUserid() {
        return userid;
    }
}