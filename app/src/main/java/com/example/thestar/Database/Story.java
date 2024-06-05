package com.example.thestar.Database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Story implements Parcelable {
    private String Name;
    private String Description;
    private String Genre;
    private String Rating;
   private String Photo;

   public Story()
   {

   }

    public Story(String name, String description, String genre, String rating, String photo) {
        Name = name;
        Description = description;
        Genre = genre;
        Rating = rating;
        Photo = photo;
    }

    protected Story(Parcel in) {
        Name = in.readString();
        Description = in.readString();
        Genre = in.readString();
        Rating = in.readString();
        Photo = in.readString();
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


    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
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
                ", Image=" + Photo +
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
        parcel.writeString(Photo);
    }
}
