package ru.geekbrains;

public class Notebook {
    String brandName;
    int price;
    int ramSize;

    public Notebook(String brandName, int price, int ramSize) {
        this.brandName = brandName;
        this.price = price;
        this.ramSize = ramSize;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRamSize() {
        return ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s", brandName, price, ramSize);
    }
}
