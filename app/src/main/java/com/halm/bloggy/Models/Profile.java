package com.halm.bloggy.Models;

public class Profile {
    private int id;
    private String age;
    private int gender;
    private int user;

    public Profile( String age, int gender, int user) {
        this.age = age;
        this.gender = gender;
        this.user = user;
    }
    public Profile(){}

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

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
