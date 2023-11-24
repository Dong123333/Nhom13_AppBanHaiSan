package com.example.nhom13_appbanhaisan.Model;

public class Product {
    private String anh;
    private String ten_san_pham;
    private int gia;
    private int so_luong_da_ban;
    private String quy_cach;
    private String mo_ta;
    private String mon_ngon;
    private String tinh_trang;
    private String xuat_xu;
    private int so_luong_ton_kho;
    public Product(){}

    public Product(String anh, String ten_san_pham, int gia, int so_luong_da_ban, String quy_cach, String mo_ta, String mon_ngon, String tinh_trang, String xuat_xu, int so_luong_ton_kho) {
        this.anh = anh;
        this.ten_san_pham = ten_san_pham;
        this.gia = gia;
        this.so_luong_da_ban = so_luong_da_ban;
        this.quy_cach = quy_cach;
        this.mo_ta = mo_ta;
        this.mon_ngon = mon_ngon;
        this.tinh_trang = tinh_trang;
        this.xuat_xu = xuat_xu;
        this.so_luong_ton_kho = so_luong_ton_kho;
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

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSo_luong_da_ban() {
        return so_luong_da_ban;
    }

    public void setSo_luong_da_ban(int so_luong_da_ban) {
        this.so_luong_da_ban = so_luong_da_ban;
    }

    public String getQuy_cach() {
        return quy_cach;
    }

    public void setQuy_cach(String quy_cach) {
        this.quy_cach = quy_cach;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getMon_ngon() {
        return mon_ngon;
    }

    public void setMon_ngon(String mon_ngon) {
        this.mon_ngon = mon_ngon;
    }

    public String getTinh_trang() {
        return tinh_trang;
    }

    public void setTinh_trang(String tinh_trang) {
        this.tinh_trang = tinh_trang;
    }

    public String getXuat_xu() {
        return xuat_xu;
    }

    public void setXuat_xu(String xuat_xu) {
        this.xuat_xu = xuat_xu;
    }

    public Integer getSo_luong_ton_kho() {
        return so_luong_ton_kho;
    }

    public void setSo_luong_ton_kho(int so_luong_ton_kho) {
        this.so_luong_ton_kho = so_luong_ton_kho;
    }
}
