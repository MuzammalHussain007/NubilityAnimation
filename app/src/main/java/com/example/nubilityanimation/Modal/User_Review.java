package com.example.nubilityanimation.Modal;

public class User_Review {
    private String reviewID;
    private String no_of_Reviews;
    private String video_id;


    public User_Review()
    {

    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getNo_of_Reviews() {
        return no_of_Reviews;
    }

    public void setNo_of_Reviews(String no_of_Reviews) {
        this.no_of_Reviews = no_of_Reviews;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public User_Review(String reviewID, String no_of_Reviews, String video_id) {
        this.reviewID = reviewID;
        this.no_of_Reviews = no_of_Reviews;
        this.video_id = video_id;
    }
}
