package com.halm.bloggy.Models;

public class User {
    private int id;
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    //region CONSTRUTOR CADASTRO
    public User(String username, String first_name, String last_name, String email, String password) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }
    public User(int id, String username, String first_name, String last_name, String email) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.id = id;
    }

    //endregion

    //region CONSTRUTOR LOGIN
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //endregion


    //region GETTERS & SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
       return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //endregion
}
