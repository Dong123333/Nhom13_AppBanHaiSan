package com.example.nhom13_appbanhaisan.Model;

public class Category {
    private String anh;
    private String ten_san_pham;
    public Category(){}

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

    public Category(String anh, String ten_san_pham) {
        this.anh = anh;
        this.ten_san_pham = ten_san_pham;
    }
}
