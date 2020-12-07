package com.example.mobile_coding_challenge;

public class Repository {

    private String Repository_name;
    private String Repository_description;
    private String Repository_number_of_stars;
    private String Repository_owner_name;
    private String Repository_avatar;
    private int Repository_avatar_image;
    private int Repository_star_image;

    public Repository() {}

    public Repository(String repository_name, String repository_description, String repository_number_of_stars, String repository_owner_name, String repository_avatar, int repository_avatar_image, int repository_star_image) {
        Repository_name = repository_name;
        Repository_description = repository_description;
        Repository_number_of_stars = repository_number_of_stars;
        Repository_owner_name = repository_owner_name;
        Repository_avatar = repository_avatar;
        Repository_avatar_image = repository_avatar_image;
        Repository_star_image = repository_star_image;
    }

    public String getRepository_name() {
        return Repository_name;
    }

    public void setRepository_name(String repository_name) {
        Repository_name = repository_name;
    }

    public String getRepository_description() {
        return Repository_description;
    }

    public void setRepository_description(String repository_description) {
        Repository_description = repository_description;
    }

    public String getRepository_number_of_stars() {
        return Repository_number_of_stars;
    }

    public void setRepository_number_of_stars(String repository_number_of_stars) {
        Repository_number_of_stars = repository_number_of_stars;
    }

    public String getRepository_owner_name() {
        return Repository_owner_name;
    }

    public void setRepository_owner_name(String repository_owner_name) {
        Repository_owner_name = repository_owner_name;
    }

    public String getRepository_avatar() {
        return Repository_avatar;
    }

    public void setRepository_avatar(String repository_avatar) {
        Repository_avatar = repository_avatar;
    }

}
