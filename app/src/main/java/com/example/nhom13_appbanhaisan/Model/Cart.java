package com.example.nhom13_appbanhaisan.Model;

public class Cart {
    private String anh,ten,quyCach;
    private int gia;
    private int soCan;
    private int soTien;

    public Cart(){}

    public Cart(String anh, String ten, String quyCach, int gia, int soCan, int soTien) {
        this.anh = anh;
        this.ten = ten;
        this.quyCach = quyCach;
        this.gia = gia;
        this.soCan = soCan;
        this.soTien = soTien;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getQuyCach() {
        return quyCach;
    }

    public void setQuyCach(String quyCach) {
        this.quyCach = quyCach;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoCan() {
        return soCan;
    }

    public void setSoCan(int soCan) {
        this.soCan = soCan;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }
}
