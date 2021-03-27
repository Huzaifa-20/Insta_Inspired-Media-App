package com.huzaifa.wallahabibi;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile {
    String myId;
    String profileImage;
    String name;
    String phoneNumber;
    String Bio;
    int totalFollowers;
    int totalFollowing;
    int totalPosts;
    HashMap<String,String> followers;
    HashMap<String,String> following;
    HashMap<String,String> posts;
    HashMap<String,Story> stories;
    String showLastSeen;
    String showProfilePhoto;
    String showBio;
    String showStatus;

    public Profile() {

    }

    public Profile(String myId,String profileImage, String name, String phoneNumber, String Bio, int totalFollowers,
                   int totalFollowing, int totalPosts,HashMap<String,String> followers, HashMap<String,String> following, HashMap<String,String> posts,
                   HashMap<String,Story> stories, String showLastSeen, String showProfilePhoto, String showBio, String showStatus) {
        this.myId=myId;
        this.profileImage=profileImage;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.Bio=Bio;
        this.totalFollowers=totalFollowers;
        this.totalFollowing=totalFollowing;
        this.totalPosts=totalPosts;
        this.followers=followers;
        this.following=following;
        this.posts=posts;
        this.stories=stories;
        this.showLastSeen=showLastSeen;
        this.showProfilePhoto=showProfilePhoto;
        this.showBio=showBio;
        this.showStatus=showStatus;
    }

    public Profile(String name,String url) {
        this.name=name;
        this.profileImage=url;
    }

    public Profile(Profile prof) {
        this.myId=prof.getMyId();
        this.profileImage = prof.getProfileImage();
        this.name = prof.getName();
        this.phoneNumber = prof.getPhoneNumber();
        this.Bio = prof.getBio();
        this.totalFollowers=prof.totalFollowers;
        this.totalFollowing=prof.totalFollowing;
        this.totalPosts=prof.totalPosts;
        this.followers=prof.followers;
        this.following=prof.following;
        this.posts=prof.posts;
        this.stories=prof.stories;
        this.showLastSeen=prof.showLastSeen;
        this.showProfilePhoto=prof.showProfilePhoto;
        this.showBio=prof.showBio;
        this.showStatus=prof.showStatus;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public int getTotalFollowers() {
        return totalFollowers;
    }

    public void setTotalFollowers(int totalFollowers) {this.totalFollowers = totalFollowers;}

    public int getTotalFollowing() {
        return totalFollowing;
    }

    public void setTotalFollowing(int totalFollowing) {
        this.totalFollowing = totalFollowing;
    }

    public String isShowLastSeen() {
        return showLastSeen;
    }

    public void setShowLastSeen(String showLastSeen) {
        this.showLastSeen = showLastSeen;
    }

    public String isShowProfilePhoto() {
        return showProfilePhoto;
    }

    public void setShowProfilePhoto(String showProfilePhoto) {
        this.showProfilePhoto = showProfilePhoto;
    }

    public String isShowBio() {
        return showBio;
    }

    public void setShowBio(String showBio) {
        this.showBio = showBio;
    }

    public String isShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    public HashMap<String,String> getFollowers() {
        return followers;
    }

    public void setFollowers(HashMap<String,String> followers) {
        this.followers = followers;
    }

    public HashMap<String,String> getFollowing() {
        return following;
    }

    public void setFollowing(HashMap<String,String> following) {
        this.following = following;
    }

    public HashMap<String,String> getPosts() {
        return posts;
    }

    public void setPosts(HashMap<String,String> posts) {
        this.posts = posts;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }

    public HashMap<String, Story> getStories() {
        return stories;
    }

    public void setStories(HashMap<String, Story> stories) {
        this.stories = stories;
    }
}
