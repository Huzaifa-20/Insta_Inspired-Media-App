package com.huzaifa.wallahabibi;

public class Posts {
    String url;
    String userId;
    String date;
    String likes;
    String shares;
    String views;


    public Posts() {
    }

    public Posts(String url, String userId, String date, String likes, String shares, String views) {
        this.url = url;
        this.userId = userId;
        this.date = date;
        this.likes = likes;
        this.shares = shares;
        this.views = views;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}
