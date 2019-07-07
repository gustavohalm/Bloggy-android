package com.halm.bloggy.Models;

public class Post {
    private int id;
    private int author;
    private String title;
    private String content;
    private  String create_date;

    public Post(int id, int author, String title, String content, String create_date) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.create_date = create_date;
    }

    public Post(String title, String content, String create_date)
    {
        this.title = title;
        this.content = content;
        this.create_date = create_date;
    }
    public Post(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
