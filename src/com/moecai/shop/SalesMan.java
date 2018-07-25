package com.moecai.shop;

public class SalesMan {
    private int sId;
    private String sName;
    private String sPassword;

    public SalesMan(int sId, String sPassword) {
        this.sId = sId;
        this.sPassword = sPassword;
    }

    public SalesMan(int sId, String sName, String sPassword) {
        this.sId = sId;
        this.sName = sName;
        this.sPassword = sPassword;
    }

    public SalesMan(String sName, String sPassword) {
        this.sName = sName;
        this.sPassword = sPassword;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public int getsId() {
        return sId;
    }

    public String getsName() {
        return sName;
    }

    public String getsPassword() {
        return sPassword;
    }
}
