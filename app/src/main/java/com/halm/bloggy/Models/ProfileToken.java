package com.halm.bloggy.Models;

public class ProfileToken {
    private int id;
    private String age;
    private int gender;
    private User user;

    public ProfileToken( String age, int gender, User user) {
        this.age = age;
        this.gender = gender;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
