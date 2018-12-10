package com.community.jboss.visitingcard.model;

public class CardListItem {
    String uid, name, email;
    String avatarUrl;
    public CardListItem(){

    }

    public CardListItem(String uid, String avatarUrl, String name, String email) {
        this.uid = uid;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
