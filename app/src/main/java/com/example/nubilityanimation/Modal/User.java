package com.example.nubilityanimation.Modal;

public class User {
    private String firstname,pastname ,type,image;
    public User()
    {

    }

    public String getFirstname() {
        return firstname;
    }

    public String getPastname() {
        return pastname;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public User(String firstname, String pastname, String type, String image) {
        this.firstname = firstname;
        this.pastname = pastname;
        this.type = type;
        this.image = image;
    }
}
