package com.example.demo.model;

import java.time.LocalDateTime;

public class NhanVienRequest {
    private Integer id_nhan_vien;
    private String ho_ten;
    private String gioi_tinh;
    private int tuoi;
    private double luong;
    private int thoi_gian_gan_bo;
    private int diem_thuong;
    private int diem_danh;
    private String so_dien_thoai;
    private String dia_chi;
    private String chuc_vu;
    private LocalDateTime ngay_vao_lam;

    public NhanVienRequest() {
    }

    public Integer getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(Integer id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public String getHo_ten() {
        return ho_ten;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public String getGioi_tinh() {
        return gioi_tinh;
    }

    public void setGioi_tinh(String gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public int getThoi_gian_gan_bo() {
        return thoi_gian_gan_bo;
    }

    public void setThoi_gian_gan_bo(int thoi_gian_gan_bo) {
        this.thoi_gian_gan_bo = thoi_gian_gan_bo;
    }

    public int getDiem_thuong() {
        return diem_thuong;
    }

    public void setDiem_thuong(int diem_thuong) {
        this.diem_thuong = diem_thuong;
    }

    public int getDiem_danh() {
        return diem_danh;
    }

    public void setDiem_danh(int diem_danh) {
        this.diem_danh = diem_danh;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public String getChuc_vu() {
        return chuc_vu;
    }

    public void setChuc_vu(String chuc_vu) {
        this.chuc_vu = chuc_vu;
    }

    public LocalDateTime getNgay_vao_lam() {
        return ngay_vao_lam;
    }

    public void setNgay_vao_lam(LocalDateTime ngay_vao_lam) {
        this.ngay_vao_lam = ngay_vao_lam;
    }
}
