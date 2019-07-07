package com.halm.bloggy.Models;

import java.util.List;


public class Paginate_Post {
    private int count;
    private String next;
    private String previous;
    private List<Post> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Post> getResults() {
        return results;
    }

    public void setResults(List<Post> results) {
        this.results = results;
    }
}
