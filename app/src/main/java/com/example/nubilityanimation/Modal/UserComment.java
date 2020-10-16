package com.example.nubilityanimation.Modal;

public class UserComment {
    private String commentid,username,userImage,commentTxt;

    public UserComment()
    {

    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getCommentTxt() {
        return commentTxt;
    }

    public void setCommentTxt(String commentTxt) {
        this.commentTxt = commentTxt;
    }

    public UserComment(String commentid, String username, String userImage, String commentTxt) {
        this.commentid = commentid;
        this.username = username;
        this.userImage = userImage;
        this.commentTxt = commentTxt;
    }
}
