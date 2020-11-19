package com.example.nubilityanimation.Modal;

public class UserCommentVideo {
    private String comment_id,video_id,username,userText,noOfreview;

    public UserCommentVideo(String comment_id, String video_id, String username, String userText, String noOfreview) {
        this.comment_id = comment_id;
        this.video_id = video_id;
        this.username = username;
        this.userText = userText;
        this.noOfreview = noOfreview;
    }
    public UserCommentVideo()
    {

    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getNoOfreview() {
        return noOfreview;
    }

    public void setNoOfreview(String noOfreview) {
        this.noOfreview = noOfreview;
    }
}
