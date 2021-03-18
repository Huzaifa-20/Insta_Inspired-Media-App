package com.huzaifa.wallahabibi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Profile {
    String myId;
    String profileImage;
    String name;
    String phoneNumber;
    String Bio;
    int followers;
    int following;
    boolean showLastSeen;
    boolean showProfilePhoto;
    boolean showBio;
    boolean showStatus;

    public Profile() {
        name="New User";
        Bio="Not known";
        profileImage="https://www.logolynx.com/images/logolynx/97/97e88682fa82ed11f3bf96dcf8479635.png";
    }

    public Profile(String myId,String profileImage, String name, String phoneNumber, String Bio) {
        this.myId=myId;
        this.profileImage=profileImage;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.Bio=Bio;
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
        this.followers=prof.followers;
        this.following=prof.following;
        this.showLastSeen=prof.showLastSeen;
        this.showProfilePhoto=prof.showProfilePhoto;
        this.showBio=prof.showBio;
        this.showStatus=prof.showStatus;
    }

    public Profile(String id, String profileImage, String name, String phoneNumber, String bio,int followers,int following,
                   boolean showLastSeen,boolean showProfilePhoto,boolean showBio,boolean showStatus) {
        this.myId=id;
        this.profileImage = profileImage;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.Bio = bio;this.followers=followers;
        this.following=following;
        this.showLastSeen=showLastSeen;
        this.showProfilePhoto=showProfilePhoto;
        this.showBio=showBio;
        this.showStatus=showStatus;
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

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean isShowLastSeen() {
        return showLastSeen;
    }

    public void setShowLastSeen(boolean showLastSeen) {
        this.showLastSeen = showLastSeen;
    }

    public boolean isShowProfilePhoto() {
        return showProfilePhoto;
    }

    public void setShowProfilePhoto(boolean showProfilePhoto) {
        this.showProfilePhoto = showProfilePhoto;
    }

    public boolean isShowBio() {
        return showBio;
    }

    public void setShowBio(boolean showBio) {
        this.showBio = showBio;
    }

    public boolean isShowStatus() {
        return showStatus;
    }

    public void setShowStatus(boolean showStatus) {
        this.showStatus = showStatus;
    }
}
