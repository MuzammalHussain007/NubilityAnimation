package com.example.nubilityanimation.Modal;

public class PostItem {
    String postid,postdescription,postimage,username,userimage;
    public PostItem()
    {

    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostdescription() {
        return postdescription;
    }

    public void setPostdescription(String postdescription) {
        this.postdescription = postdescription;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public PostItem(String postid, String postdescription, String postimage, String username, String userimage) {
        this.postid = postid;
        this.postdescription = postdescription;
        this.postimage = postimage;
        this.username = username;
        this.userimage = userimage;
    }
}
