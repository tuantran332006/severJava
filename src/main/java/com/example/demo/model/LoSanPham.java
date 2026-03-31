package com.example.demo.model;

import java.time.LocalDateTime;

public class LoSanPham {

    private int id_lo;
    private int id_sp;
    private int id_phieu_nhap;
    private int so_luong_nhap;
    private int so_luong_con;
    private double don_gia_nhap;
    private LocalDateTime ngay_nhap;
    private LocalDateTime ngay_san_xuat;
    private LocalDateTime han_su_dung;

    public LoSanPham() {
    }

    public LoSanPham(int id_lo,
                     int id_sp,
                     int id_phieu_nhap,
                     int so_luong_nhap,
                     int so_luong_con,
                     double don_gia_nhap,
                     LocalDateTime ngay_nhap,
                     LocalDateTime ngay_san_xuat,
                     LocalDateTime han_su_dung) {
        this.id_lo = id_lo;
        this.id_sp = id_sp;
        this.id_phieu_nhap = id_phieu_nhap;
        this.so_luong_nhap = so_luong_nhap;
        this.so_luong_con = so_luong_con;
        this.don_gia_nhap = don_gia_nhap;
        this.ngay_nhap = ngay_nhap;
        this.ngay_san_xuat = ngay_san_xuat;
        this.han_su_dung = han_su_dung;
    }

    public int getId_lo() {
        return id_lo;
    }

    public void setId_lo(int id_lo) {
        this.id_lo = id_lo;
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public int getId_phieu_nhap() {
        return id_phieu_nhap;
    }

    public void setId_phieu_nhap(int id_phieu_nhap) {
        this.id_phieu_nhap = id_phieu_nhap;
    }

    public int getSo_luong_nhap() {
        return so_luong_nhap;
    }

    public void setSo_luong_nhap(int so_luong_nhap) {
        this.so_luong_nhap = so_luong_nhap;
    }

    public int getSo_luong_con() {
        return so_luong_con;
    }

    public void setSo_luong_con(int so_luong_con) {
        this.so_luong_con = so_luong_con;
    }

    public double getDon_gia_nhap() {
        return don_gia_nhap;
    }

    public void setDon_gia_nhap(double don_gia_nhap) {
        this.don_gia_nhap = don_gia_nhap;
    }

    public LocalDateTime getNgay_nhap() {
        return ngay_nhap;
    }

    public void setNgay_nhap(LocalDateTime ngay_nhap) {
        this.ngay_nhap = ngay_nhap;
    }

    public LocalDateTime getNgay_san_xuat() {
        return ngay_san_xuat;
    }

    public void setNgay_san_xuat(LocalDateTime ngay_san_xuat) {
        this.ngay_san_xuat = ngay_san_xuat;
    }

    public LocalDateTime getHan_su_dung() {
        return han_su_dung;
    }

    public void setHan_su_dung(LocalDateTime han_su_dung) {
        this.han_su_dung = han_su_dung;
    }

    @Override
    public String toString() {
        return "LoSanPham{" +
                "id_lo=" + id_lo +
                ", id_sp=" + id_sp +
                ", id_phieu_nhap=" + id_phieu_nhap +
                ", so_luong_nhap=" + so_luong_nhap +
                ", so_luong_con=" + so_luong_con +
                ", don_gia_nhap=" + don_gia_nhap +
                ", ngay_nhap=" + ngay_nhap +
                ", ngay_san_xuat=" + ngay_san_xuat +
                ", han_su_dung=" + han_su_dung +
                '}';
    }
}