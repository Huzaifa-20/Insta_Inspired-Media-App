package com.huzaifa.wallahabibi;

public class ProfilePosts {
    String firstPost;
    String secondPost;
    String thirdPost;

    public ProfilePosts() {
    }

    public ProfilePosts(String p1){
        firstPost=p1;
    }

    public ProfilePosts(String p1, String p2){
        firstPost=p1;
        secondPost=p2;
    }

    public ProfilePosts(String p1, String p2, String p3){
        firstPost=p1;
        secondPost=p2;
        thirdPost=p3;
    }



    public String getFirstPost() {
        return firstPost;
    }

    public void setFirstPost(String firstPost) {
        this.firstPost = firstPost;
    }

    public String getSecondPost() {
        return secondPost;
    }

    public void setSecondPost(String secondPost) {
        this.secondPost = secondPost;
    }

    public String getThirdPost() {
        return thirdPost;
    }

    public void setThirdPost(String thirdPost) {
        this.thirdPost = thirdPost;
    }
}
