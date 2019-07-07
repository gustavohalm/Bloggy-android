package com.halm.bloggy.Models;

public class Comment {
    private int id;
    private String author;
    private int post;
    private String content;

    public Comment(int id, String author, int post, String content) {
        this.id = id;
        this.author = author;
        this.post = post;
        this.content = content;
    }

    public Comment(String author, int post, String content) {
        this.author = author;
        this.post = post;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
