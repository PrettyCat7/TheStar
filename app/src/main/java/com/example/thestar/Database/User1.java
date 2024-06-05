package com.example.thestar.Database;

import android.os.Parcel;


public class User1 {


    private String firstName;
    private String lastName;
    private String username;
    private String photo;

        public User1(Parcel in) {
        }

    public User1(String firstName, String lastName, String username, String photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.photo = photo;
    }

    public User1() {

    }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPhoto() {
                return photo;
        }

        public void setPhoto(String photo) {
                this.photo = photo;
        }

        @Override
        public String toString() {
                return "User1{" +
                        "firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", username='" + username + '\'' +
                        ", photo='" + photo + '\'' +
                        '}';
        }
}


