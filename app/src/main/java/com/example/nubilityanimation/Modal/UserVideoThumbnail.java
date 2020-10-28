package com.example.nubilityanimation.Modal;

public class UserVideoThumbnail {
    private String id,name,picURL;

    public UserVideoThumbnail(String id, String name, String picURL) {
        this.id = id;
        this.name = name;
        this.picURL = picURL;
    }
    public UserVideoThumbnail()
    {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }
}
