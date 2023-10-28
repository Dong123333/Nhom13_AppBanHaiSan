package com.example.nhom13_appbanhaisan.Model;

public class Cart {
    private String anh,ten,quyCach;
    private String gia;
    private long soCan;
    private long soTien;
    public Cart(){}

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

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public long getSoCan() {
        return soCan;
    }

    public void setSoCan(long soCan) {
        this.soCan = soCan;
    }

    public long getSoTien() {
        return soTien;
    }

    public void setSoTien(long soTien) {
        this.soTien = soTien;
    }
}
