package com.example.noble_mall2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Review implements Parcelable{
    private int Groceryitemid;
    private String Username;
    private String date;
    private String text;

    public Review(int groceryitemid, String username, String date, String text) {
        Groceryitemid = groceryitemid;
        Username = username;
        this.date = date;
        this.text = text;
    }

    protected Review(Parcel in) {
        Groceryitemid = in.readInt();
        Username = in.readString();
        date = in.readString();
        text = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int getGroceryitemid() {
        return Groceryitemid;
    }

    public void setGroceryitemid(int groceryitemid) {
        Groceryitemid = groceryitemid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "Groceryitemid=" + Groceryitemid +
                ", Username='" + Username + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(Groceryitemid);
        dest.writeString(Username);
        dest.writeString(date);
        dest.writeString(text);
    }
}
