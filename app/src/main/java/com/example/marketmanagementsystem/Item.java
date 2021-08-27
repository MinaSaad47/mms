package com.example.marketmanagementsystem;

public class Item {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String imageURL;

    public Item(int id, String name, double price, int quantity, String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
