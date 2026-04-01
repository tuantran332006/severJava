package com.example.demo.model;

import java.time.LocalDateTime;
public class HoaDonRequest {
    private int id_hoa_don;
    private int id_kh;
    private int id_nhan_vien;
    private LocalDateTime ngay_lap;
    private double tong_tien;
    private double giam_gia;
    private String phuong_thuc_thanh_toan;
    private String ghi_chu;

    public int getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(int id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
    }

    public int getId_kh() {
        return id_kh;
    }

    public void setId_kh(int id_kh) {
        this.id_kh = id_kh;
    }

    public Integer getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(Integer id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public LocalDateTime getNgay_lap() {
        return ngay_lap;
    }

    public void setNgay_lap(LocalDateTime ngay_lap) {
        this.ngay_lap = ngay_lap;
    }

    public double getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(double tong_tien) {
        this.tong_tien = tong_tien;
    }

    public double getGiam_gia() {
        return giam_gia;
    }

    public void setGiam_gia(double giam_gia) {
        this.giam_gia = giam_gia;
    }

    public String getPhuong_thuc_thanh_toan() {
        return phuong_thuc_thanh_toan;
    }

    public void setPhuong_thuc_thanh_toan(String phuong_thuc_thanh_toan) {
        this.phuong_thuc_thanh_toan = phuong_thuc_thanh_toan;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public HoaDonRequest() {
    }

}
