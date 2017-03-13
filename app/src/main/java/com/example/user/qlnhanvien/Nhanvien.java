package com.example.user.qlnhanvien;

/**
 * Created by user on 3/12/2017.
 */

public class Nhanvien {
    public String ma;
    public String ten;
    public String chucvu;
    public String luong;

    public Nhanvien() {
    }

    public Nhanvien(String ma, String ten, String chucvu, String luong) {
        this.ma = ma;
        this.ten = ten;
        this.chucvu = chucvu;
        this.luong = luong;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public String getLuong() {
        return luong;
    }

    public void setLuong(String luong) {
        this.luong = luong;
    }
}
