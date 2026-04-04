package com.example.demo.model;

public class ChiTietHoaDon {

    private Integer id_chi_tiet;
    private Integer id_lo_san_pham;
    private Integer id_hoa_don;
    private Integer id_san_pham;
    private Integer so_luong;
    private double don_gia;
    private double thanh_tien;

    public ChiTietHoaDon() {}

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "id_chi_tiet=" + id_chi_tiet +
                ", id_lo_san_pham=" + id_lo_san_pham +
                ", id_hoa_don=" + id_hoa_don +
                ", id_san_pham=" + id_san_pham +
                ", so_luong=" + so_luong +
                ", don_gia=" + don_gia +
                ", thanh_tien=" + thanh_tien +
                '}';
    }

    public Integer getId_chi_tiet() {
        return id_chi_tiet;
    }

    public void setId_chi_tiet(Integer id_chi_tiet) {
        this.id_chi_tiet = id_chi_tiet;
    }

    public Integer getId_lo_san_pham() {
        return id_lo_san_pham;
    }

    public void setId_lo_san_pham(Integer id_lo_san_pham) {
        this.id_lo_san_pham = id_lo_san_pham;
    }

    public Integer getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(Integer id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
    }

    public Integer getId_san_pham() {
        return id_san_pham;
    }

    public void setId_san_pham(Integer id_san_pham) {
        this.id_san_pham = id_san_pham;
    }

    public Integer getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(Integer so_luong) {
        this.so_luong = so_luong;
    }

    public double getDon_gia() {
        return don_gia;
    }

    public void setDon_gia(double don_gia) {
        this.don_gia = don_gia;
    }

    public double getThanh_tien() {
        return thanh_tien;
    }

    public void setThanh_tien(double thanh_tien) {
        this.thanh_tien = thanh_tien;
    }

    public ChiTietHoaDon(Integer id_chi_tiet, Integer id_lo_san_pham, Integer id_hoa_don, Integer id_san_pham, Integer so_luong, double don_gia, double thanh_tien) {
        this.id_chi_tiet = id_chi_tiet;
        this.id_lo_san_pham = id_lo_san_pham;
        this.id_hoa_don = id_hoa_don;
        this.id_san_pham = id_san_pham;
        this.so_luong = so_luong;
        this.don_gia = don_gia;
        this.thanh_tien = thanh_tien;
    }
}
