package com.community.jboss.visitingcard.model;

public class Contributor {
    private String avatarUrl, name;
    private int contibutions;

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

    public int getContibutions() {
        return contibutions;
    }

    public void setContibutions(int contibutions) {
        this.contibutions = contibutions;
    }
}
