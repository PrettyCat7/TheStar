package com.example.thestar;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.jar.Attributes;

public class Story implements Parcelable {
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

    protected Story(Parcel in) {
        Name = in.readString();
        Description = in.readString();
        Genre = in.readString();
        Rating = in.readString();
        Image = in.readString();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Description);
        parcel.writeString(Genre);
        parcel.writeString(Rating);
        parcel.writeString(Image);
    }
}
