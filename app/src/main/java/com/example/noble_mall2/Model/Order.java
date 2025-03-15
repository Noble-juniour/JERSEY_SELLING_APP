package com.example.noble_mall2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.noble_mall2.Utils;

import java.util.ArrayList;

public class Order implements Parcelable {
    private int id;
    private ArrayList<Integer> items;
    private String address;
    private String email;
    private String phonenumber;
    private String zipcode;
    private String paymentmethod;
    private boolean success;
    private double totalPrice;

    public Order() {
    }

    public Order(ArrayList<Integer> items, String address, String email, String phonenumber, String zipcode, String paymentmethod, boolean success, double totalPrice) {
        this.id = Utils.getOrderId();
        this.items = items;
        this.address = address;
        this.email = email;
        this.phonenumber = phonenumber;
        this.zipcode = zipcode;
        this.paymentmethod = paymentmethod;
        this.success = success;
        this.totalPrice = totalPrice;
    }

    protected Order(Parcel in) {
        id = in.readInt();
        address = in.readString();
        email = in.readString();
        phonenumber = in.readString();
        zipcode = in.readString();
        paymentmethod = in.readString();
        success = in.readByte() != 0;
        totalPrice = in.readDouble();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getItems() {
        return items;
    }

    public void setItems(ArrayList<Integer> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", paymentmethod='" + paymentmethod + '\'' +
                ", success=" + success +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(phonenumber);
        dest.writeString(zipcode);
        dest.writeString(paymentmethod);
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeDouble(totalPrice);
    }
}
