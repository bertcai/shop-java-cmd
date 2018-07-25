package com.moecai.shop;

public class Goods {
    private int id;
    private String name;
    private float price;
    private int num; //库存
    private int shopNum = 0;

    public Goods() {
        id = -1;
        name = "";
        price = 0;
        num = 0;
    }

    public Goods(int id, String name, float price, int num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }
}
