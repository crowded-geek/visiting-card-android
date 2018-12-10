package com.community.jboss.visitingcard.model;

public class CardModel {

    private String avatarUrl;
    private String name;
    private String phone;
    private String email;
    private String linkedin;
    private String twitter;
    private String github;


    public CardModel(String name, String phone, String email, String twitter, String linkedin, String github, String avatarUrl){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.github = github;
        this.avatarUrl = avatarUrl;
    }

    public CardModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
