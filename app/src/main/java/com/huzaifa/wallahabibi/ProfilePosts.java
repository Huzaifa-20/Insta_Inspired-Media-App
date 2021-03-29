package com.huzaifa.wallahabibi;

public class ProfilePosts {
    String firstPost;
    String secondPost;
    String thirdPost;

    String firstType;
    String secondType;
    String thirdType;

    public ProfilePosts() {
    }

    public ProfilePosts(String p1,String t1){
        firstPost=p1;
        firstType=t1;

        secondPost="";
        secondType="";
        thirdPost="";
        thirdType="";
    }

    public ProfilePosts(String p1, String t1, String p2, String t2){
        firstPost=p1;
        firstType=t1;
        secondPost=p2;
        secondType=t2;

        thirdPost="";
        thirdType="";
    }

    public ProfilePosts(String p1, String t1, String p2, String t2, String p3,String t3){
        firstPost=p1;
        firstType=t1;
        secondPost=p2;
        secondType=t2;
        thirdPost=p3;
        thirdType=t3;
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

    public String getFirstType() {
        return firstType;
    }

    public void setFirstType(String firstType) {
        this.firstType = firstType;
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    public String getThirdType() {
        return thirdType;
    }

    public void setThirdType(String thirdType) {
        this.thirdType = thirdType;
    }
}
