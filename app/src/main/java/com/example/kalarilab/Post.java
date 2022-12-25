package com.example.kalarilab;

public class Post {
    private String level;
    private String challenge;
    private String fullName;
    private String userId;
    private String  uri;


    public Post(String userId, String level, String challenge, String fullName, String uri) {
        this.userId = userId;
        this.level = level;
        this.challenge = challenge;
        this.fullName = fullName;
        this.uri = uri;

    }

    public Post() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
