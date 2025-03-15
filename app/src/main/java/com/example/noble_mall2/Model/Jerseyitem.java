package com.example.noble_mall2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.noble_mall2.Utils;

import java.util.ArrayList;

public class Jerseyitem implements Parcelable {
    private int id;
    private String name;
    private String Description;
    private String imageurl;
    private String category;
    private int availableAmount;
    private double price;
    private int popularityPoint;
    private int userPoint;
    private int rate;
    private ArrayList<Review> reviews;

    public Jerseyitem(String name, String description, String imageurl, String category,
                      int availableAmount, double price) {
        this.id = Utils.getID();
        this.name = name;
        Description = description;
        this.imageurl = imageurl;
        this.category = category;
        this.availableAmount = availableAmount;
        this.price = price;
        this.popularityPoint = 0;
        this.userPoint = 0;
        this.rate = 0;
        this.reviews = new ArrayList<>();
    }


    protected Jerseyitem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        Description = in.readString();
        imageurl = in.readString();
        category = in.readString();
        availableAmount = in.readInt();
        price = in.readDouble();
        popularityPoint = in.readInt();
        userPoint = in.readInt();
        rate = in.readInt();
        reviews = in.createTypedArrayList(Review.CREATOR);
    }

    public static final Creator<Jerseyitem> CREATOR = new Creator<Jerseyitem>() {
        @Override
        public Jerseyitem createFromParcel(Parcel in) {
            return new Jerseyitem(in);
        }

        @Override
        public Jerseyitem[] newArray(int size) {
            return new Jerseyitem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Jerseyitem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", category='" + category + '\'' +
                ", availableAmount=" + availableAmount +
                ", price=" + price +
                ", popularityPoint=" + popularityPoint +
                ", userPoint=" + userPoint +
                ", rate=" + rate +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(Description);
        dest.writeString(imageurl);
        dest.writeString(category);
        dest.writeInt(availableAmount);
        dest.writeDouble(price);
        dest.writeInt(popularityPoint);
        dest.writeInt(userPoint);
        dest.writeInt(rate);
        dest.writeTypedList(reviews);
    }
}
