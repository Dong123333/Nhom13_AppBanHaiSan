package com.example.nhom13_appbanhaisan.Model;

public class Users {
    private int id;
    private String ho_va_ten;
    private String so_dien_thoai;
    private String mat_khau;
    private String nhap_lai_mat_khau;

    public Users() {

    }

    public Users(String ho_va_ten, String so_dien_thoai, String mat_khau, String nhap_lai_mat_khau) {
        this.ho_va_ten = ho_va_ten;
        this.so_dien_thoai = so_dien_thoai;
        this.mat_khau = mat_khau;
        this.nhap_lai_mat_khau = nhap_lai_mat_khau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHo_va_ten() {
        return ho_va_ten;
    }

    public void setHo_va_ten(String ho_va_ten) {
        this.ho_va_ten = ho_va_ten;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getMat_khau() {
        return mat_khau;
    }

    public void setMat_khau(String mat_khau) {
        this.mat_khau = mat_khau;
    }

    public String getNhap_lai_mat_khau() {
        return nhap_lai_mat_khau;
    }

    public void setNhap_lai_mat_khau(String nhap_lai_mat_khau) {
        this.nhap_lai_mat_khau = nhap_lai_mat_khau;
    }
}
