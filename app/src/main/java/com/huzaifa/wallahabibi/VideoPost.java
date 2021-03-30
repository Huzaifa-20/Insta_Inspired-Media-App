package com.huzaifa.wallahabibi;

public class VideoPost {
    String url;
    String thumbnail;

    public VideoPost() {
    }

    public VideoPost(String url, String thumbnail) {
        this.url = url;
        this.thumbnail = thumbnail;
    }

    public VideoPost(VideoPost videoPost) {
        this.url = videoPost.url;
        this.thumbnail = videoPost.thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
