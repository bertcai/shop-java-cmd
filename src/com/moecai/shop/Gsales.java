package com.moecai.shop;

public class Gsales {
    private int gId;
    private int sId;
    private int sNum;

    private String gName;
    private float gPrice;
    private int gNum;
    private int allSnum; //单种商品销量总和

    public Gsales(int gId, int sId, int sNum) {
        this.gId = gId;
        this.sId = sId;
        this.sNum = sNum;
    }

    public Gsales(String gName, float gPrice, int gNum, int allSnum) {
        this.gName = gName;
        this.gPrice = gPrice;
        this.gNum = gNum;
        this.allSnum = allSnum;
    }

    public float getgPrice() {
        return gPrice;
    }

    public int getAllSnum() {
        return allSnum;
    }

    public int getgId() {
        return gId;
    }

    public int getgNum() {
        return gNum;
    }

    public int getsId() {
        return sId;
    }

    public int getsNum() {
        return sNum;
    }

    public String getgName() {
        return gName;
    }

    public void setAllSnum(int allSnum) {
        this.allSnum = allSnum;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public void setgNum(int gNum) {
        this.gNum = gNum;
    }

    public void setgPrice(float gPrice) {
        this.gPrice = gPrice;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }
}
