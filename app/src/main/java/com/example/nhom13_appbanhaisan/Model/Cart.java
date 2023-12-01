package com.example.nhom13_appbanhaisan.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

public class Cart implements Parcelable {
    private String anh,ten,quyCach;
    @Exclude private String id;
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



    @Exclude public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    private void readFromParcel(Parcel in) {
        ten = in.readString();
        quyCach = in.readString();
        gia = in.readInt();
        soCan = in.readInt();
        soTien = in.readInt();
        anh = in.readString();
    }

    // Phương thức writeToParcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ten);
        dest.writeString(quyCach);
        dest.writeInt(gia);
        dest.writeInt(soCan);
        dest.writeInt(soTien);
        dest.writeString(anh);
    }

    // Tạo Creator
    public static final Parcelable.Creator<Cart> CREATOR = new Parcelable.Creator<Cart>() {
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    // Constructor tạo từ Parcel
    private Cart(Parcel in) {
        readFromParcel(in);
    }

    // Các phương thức cần triển khai Parcelable
    @Override
    public int describeContents() {
        return 0;
    }
}
