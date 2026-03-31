package com.example.demo.model;

public class ChiTietHoaDonRequest {
    private int id_chi_tiet;
    private int id_hoa_don;
    private int id_sp;
    private double don_gia;
    private int so_luong;
    private double thanh_tien;

    public ChiTietHoaDonRequest() {
    }

    public int getId_chi_tiet() {
        return id_chi_tiet;
    }

    public void setId_chi_tiet(int id_chi_tiet) {
        this.id_chi_tiet = id_chi_tiet;
    }

    public int getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(int id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public double getDon_gia() {
        return don_gia;
    }

    public void setDon_gia(double don_gia) {
        this.don_gia = don_gia;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public double getThanh_tien() {
        return thanh_tien;
    }

    public void setThanh_tien(double thanh_tien) {
        this.thanh_tien = thanh_tien;
    }
}
