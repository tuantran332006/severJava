package com.example.demo.model;

import java.time.LocalDateTime;
public class PhieuNhapRequest {
    private int id_phieu_nhap;
    private int id_ncc;
    private int id_nhan_vien;
    private LocalDateTime ngay_nhap;
    private double tong_tien;
    private String ghi_chu;

    public PhieuNhapRequest() {
    }

    public int getId_phieu_nhap() {
        return id_phieu_nhap;
    }

    public void setId_phieu_nhap(int id_phieu_nhap) {
        this.id_phieu_nhap = id_phieu_nhap;
    }

    public int getId_ncc() {
        return id_ncc;
    }

    public void setId_ncc(int id_ncc) {
        this.id_ncc = id_ncc;
    }

    public int getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(int id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public LocalDateTime getNgay_nhap() {
        return ngay_nhap;
    }

    public void setNgay_nhap(LocalDateTime ngay_nhap) {
        this.ngay_nhap = ngay_nhap;
    }

    public double getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(double tong_tien) {
        this.tong_tien = tong_tien;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }
}
