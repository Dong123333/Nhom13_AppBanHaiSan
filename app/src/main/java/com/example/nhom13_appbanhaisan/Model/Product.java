package com.example.nhom13_appbanhaisan.Model;

public class Product {
    private String anh;
    private String ten_san_pham;
    private String gia;
    private int so_luong_da_ban;
    private String quy_cach;

    public Product(){}

    public Product(String anh, String ten_san_pham, String gia, int so_luong_da_ban, String quyCach) {
        this.anh = anh;
        this.ten_san_pham = ten_san_pham;
        this.gia = gia;
        this.so_luong_da_ban = so_luong_da_ban;
        this.quy_cach = quyCach;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen_san_pham() {
        return ten_san_pham;
    }

    public void setTen_san_pham(String ten_san_pham) {
        this.ten_san_pham = ten_san_pham;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public int getSo_luong_da_ban() {
        return so_luong_da_ban;
    }

    public void setSo_luong_da_ban(int so_luong_da_ban) {
        this.so_luong_da_ban = so_luong_da_ban;
    }

    public String getQuyCach() {
        return quy_cach;
    }

    public void setQuyCach(String quyCach) {
        this.quy_cach = quyCach;
    }


}
