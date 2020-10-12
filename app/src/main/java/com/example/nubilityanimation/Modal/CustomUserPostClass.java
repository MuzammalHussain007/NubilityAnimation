package com.example.nubilityanimation.Modal;

public class CustomUserPostClass {
    private String username,userimageURL,userpostURL ,postdescription;
    public CustomUserPostClass()
    {

    }

    public String getPostdescription() {
        return postdescription;
    }

    public void setPostdescription(String postdescription) {
        this.postdescription = postdescription;
    }

    public CustomUserPostClass(String username, String userimageURL, String userpostURL, String postdescription) {
        this.username = username;
        this.userimageURL = userimageURL;
        this.userpostURL = userpostURL;
        this.postdescription = postdescription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimageURL() {
        return userimageURL;
    }

    public void setUserimageURL(String userimageURL) {
        this.userimageURL = userimageURL;
    }

    public String getUserpostURL() {
        return userpostURL;
    }

    public void setUserpostURL(String userpostURL) {
        this.userpostURL = userpostURL;
    }
}
