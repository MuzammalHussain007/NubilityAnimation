package com.example.nubilityanimation.Modal;

public class UserVideoThumbnail {
   private String thumbnailid,thumbnailName,pictureURL,videoURL,nofOFreview;

    public String getThumbnailid() {
        return thumbnailid;
    }
    public UserVideoThumbnail()
    {

    }

    public void setThumbnailid(String thumbnailid) {
        this.thumbnailid = thumbnailid;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public UserVideoThumbnail(String thumbnailid, String thumbnailName, String pictureURL, String videoURL, String nofOFreview) {
        this.thumbnailid = thumbnailid;
        this.thumbnailName = thumbnailName;
        this.pictureURL = pictureURL;
        this.videoURL = videoURL;
        this.nofOFreview = nofOFreview;
    }

    public void setNofOFreview(String nofOFreview) {
        this.nofOFreview = nofOFreview;
    }

    public String getNofOFreview() {
        return nofOFreview;
    }
}
