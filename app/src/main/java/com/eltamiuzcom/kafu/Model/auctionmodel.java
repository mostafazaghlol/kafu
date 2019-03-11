package com.eltamiuzcom.kafu.Model;

public class auctionmodel {
    private String title, time, price;
    private int image;

    public auctionmodel(String title, String time, String price, int image) {
        this.title = title;
        this.time = time;
        this.price = price;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
