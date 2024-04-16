package com.example.thestar;

import android.widget.ImageView;

import java.util.jar.Attributes;

public class Story {
    private String Name;
    private String Description;
    private String Genre;
    private String Rating ;
    private String Image;

    public Story(String name, String description, String genre, String rating) {
        Name = name;
        Description = description;
        Genre = genre;
        Rating = rating;
        Image= Image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    @Override
    public String toString() {
        return "Story{" +
                "Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Rating='" + Rating + '\'' +
                ", Image=" + Image +
                '}';
    }
}
