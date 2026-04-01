package com.example.demo.model;

public class SanPhamRequest {

    private Integer idSp;
    private String tenSp;
    private Double giaSp;
    private Integer tongSoLuongSpTrongKho;
    private Integer idLoai;
    private String donViTinh;
    private String moTa;

    public SanPhamRequest() {
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

    public Double getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(Double giaSp) {
        this.giaSp = giaSp;
    }

    public Integer getTongSoLuongSpTrongKho() {
        return tongSoLuongSpTrongKho;
    }

    public void setTongSoLuongSpTrongKho(Integer tongSoLuongSpTrongKho) {
        this.tongSoLuongSpTrongKho = tongSoLuongSpTrongKho;
    }

    public Integer getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(Integer idLoai) {
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
}