package com.huzaifa.wallahabibi;

public class Story {
    String userName;
    String story;
    String time;

    public Story() {
    }

    public Story(String userName, String story, String time) {
        this.userName = userName;
        this.story = story;
        this.time = time;
    }

    public Story(Story story) {
        this.userName = story.userName;
        this.story = story.story;
        this.time = story.time;
    }

    public Story(String story, String time) {
        this.story = story;
        this.time = time;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
