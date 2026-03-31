package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChiTietPhieuNhap {
    private int id_chi_tiet_nhap;
    private int id_phieu_nhap;
    private int id_sp;
    private int so_luong;
    private double don_gia_nhap;
    private double thanh_tien;
    // Thêm thuộc tính ngày nhập
    private LocalDateTime ngay_nhap;

    public ChiTietPhieuNhap() {
    }

    public ChiTietPhieuNhap(int id_chi_tiet_nhap, int id_phieu_nhap, int id_sp, int so_luong, 
                           double don_gia_nhap, double thanh_tien, LocalDateTime ngay_nhap) {
        this.id_chi_tiet_nhap = id_chi_tiet_nhap;
        this.id_phieu_nhap = id_phieu_nhap;
        this.id_sp = id_sp;
        this.so_luong = so_luong;
        this.don_gia_nhap = don_gia_nhap;
        this.thanh_tien = thanh_tien;
        this.ngay_nhap = ngay_nhap;
    }

    // Getter và Setter cho ngay_nhap
    public LocalDateTime getNgay_nhap() {
        return ngay_nhap;
    }

    public void setNgay_nhap(LocalDateTime ngay_nhap) {
        this.ngay_nhap = ngay_nhap;
    }

    // Phương thức hỗ trợ hiển thị ngày theo định dạng Việt Nam (dd/MM/yyyy HH:mm)
    public String getNgayNhapFormatted() {
        if (this.ngay_nhap == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.ngay_nhap.format(formatter);
    }

    // --- Các Getter và Setter cũ ---
    public int getId_chi_tiet_nhap() { return id_chi_tiet_nhap; }
    public void setId_chi_tiet_nhap(int id_chi_tiet_nhap) { this.id_chi_tiet_nhap = id_chi_tiet_nhap; }

    public int getId_phieu_nhap() { return id_phieu_nhap; }
    public void setId_phieu_nhap(int id_phieu_nhap) { this.id_phieu_nhap = id_phieu_nhap; }

    public int getId_sp() { return id_sp; }
    public void setId_sp(int id_sp) { this.id_sp = id_sp; }

    public int getSo_luong() { return so_luong; }
    public void setSo_luong(int so_luong) { this.so_luong = so_luong; }

    public double getDon_gia_nhap() { return don_gia_nhap; }
    public void setDon_gia_nhap(double don_gia_nhap) { this.don_gia_nhap = don_gia_nhap; }

    public double getThanh_tien() { return thanh_tien; }
    public void setThanh_tien(double thanh_tien) { this.thanh_tien = thanh_tien; }

    @Override
    public String toString() {
        return "ChiTietPhieuNhap{" +
                "id_chi_tiet_nhap=" + id_chi_tiet_nhap +
                ", id_phieu_nhap=" + id_phieu_nhap +
                ", id_sp=" + id_sp +
                ", so_luong=" + so_luong +
                ", don_gia_nhap=" + don_gia_nhap +
                ", thanh_tien=" + thanh_tien +
                ", ngay_nhap=" + getNgayNhapFormatted() + // Dùng định dạng cho dễ đọc
                '}';
    }
}