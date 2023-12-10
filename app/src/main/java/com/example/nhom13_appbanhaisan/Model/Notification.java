package com.example.nhom13_appbanhaisan.Model;

public class Notification {
    private String madh;
    private String trangthaidonhang;
    private String tongtien;

    public Notification(String madh, String trangthaidonhang, String tongtien) {
        this.madh = madh;
        this.trangthaidonhang = trangthaidonhang;
        this.tongtien = tongtien;
    }

    public Notification(){}

    public String getMadh() {
        return madh;
    }

    public void setMadh(String madh) {
        this.madh = madh;
    }

    public String getTrangthaidonhang() {
        return trangthaidonhang;
    }

    public void setTrangthaidonhang(String trangthaidonhang) {
        this.trangthaidonhang = trangthaidonhang;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }
}
