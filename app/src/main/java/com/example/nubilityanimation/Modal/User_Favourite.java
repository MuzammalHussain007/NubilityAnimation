package com.example.nubilityanimation.Modal;

public class User_Favourite {
    private String videoId,videoURL,pictureURL;

    public User_Favourite()
    {

    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public User_Favourite(String videoId, String videoURL, String pictureURL) {
        this.videoId = videoId;
        this.videoURL = videoURL;
        this.pictureURL = pictureURL;
    }
}
