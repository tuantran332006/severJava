package com.example.demo.model;

import java.time.LocalDateTime;

public class NhapHangItem {
    private Integer idSp; // null hoặc <= 0 nghĩa là sản phẩm mới
    private String tenSp;
    private double giaSp;
    private int idLoai;
    private String donViTinh;
    private String moTa;

    private int idNcc;
    private String tenNhaCungCap;

    private int soLuongNhap;
    private double donGiaNhap;
    private LocalDateTime ngayNhap;
    private LocalDateTime ngaySanXuat;
    private LocalDateTime hanSuDung;

    public NhapHangItem() {
    }

    public Integer getIdSp() {
        return idSp;
    }

    public void setIdSp(Integer idSp) {
        this.idSp = idSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public double getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(double giaSp) {
        this.giaSp = giaSp;
    }

    public int getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(int idLoai) {
        this.idLoai = idLoai;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdNcc() {
        return idNcc;
    }

    public void setIdNcc(int idNcc) {
        this.idNcc = idNcc;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }

    public LocalDateTime getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDateTime ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public LocalDateTime getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setNgaySanXuat(LocalDateTime ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }

    public LocalDateTime getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDateTime hanSuDung) {
        this.hanSuDung = hanSuDung;
    }
}