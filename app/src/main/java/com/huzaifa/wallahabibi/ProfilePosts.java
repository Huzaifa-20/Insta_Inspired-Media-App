package com.huzaifa.wallahabibi;

public class ProfilePosts {
    String firstPost;
    String secondPost;
    String thirdPost;
    String fourthPost;

    public ProfilePosts() {
    }

    public ProfilePosts(String p1, String p2, String p3, String p4){
        firstPost=p1;
        secondPost=p2;
        thirdPost=p3;
        fourthPost=p4;
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

    public String getFourthPost() {
        return fourthPost;
    }

    public void setFourthPost(String fourthPost) {
        this.fourthPost = fourthPost;
    }
}
