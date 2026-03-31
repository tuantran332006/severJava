package com.example.demo.model;

public class KhachHang {
    private int id_kh;
    private String ten_kh;
    private int nam_sinh_kh;
    private int diem_thuong_kh;
    private String so_dien_thoai;
    private String dia_chi;
    private String gioi_tinh;

    public KhachHang() {
    }

    public KhachHang(int id_kh, String ten_kh, int nam_sinh_kh, int diem_thuong_kh,
                     String so_dien_thoai, String dia_chi, String gioi_tinh) {
        this.id_kh = id_kh;
        this.ten_kh = ten_kh;
        this.nam_sinh_kh = nam_sinh_kh;
        this.diem_thuong_kh = diem_thuong_kh;
        this.so_dien_thoai = so_dien_thoai;
        this.dia_chi = dia_chi;
        this.gioi_tinh = gioi_tinh;
    }

    public int getId_kh() {
        return id_kh;
    }

    public void setId_kh(int id_kh) {
        this.id_kh = id_kh;
    }

    public String getTen_kh() {
        return ten_kh;
    }

    public void setTen_kh(String ten_kh) {
        this.ten_kh = ten_kh;
    }

    public int getNam_sinh_kh() {
        return nam_sinh_kh;
    }

    public void setNam_sinh_kh(int nam_sinh_kh) {
        this.nam_sinh_kh = nam_sinh_kh;
    }

    public int getDiem_thuong_kh() {
        return diem_thuong_kh;
    }

    public void setDiem_thuong_kh(int diem_thuong_kh) {
        this.diem_thuong_kh = diem_thuong_kh;
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

    public String getGioi_tinh() {
        return gioi_tinh;
    }

    public void setGioi_tinh(String gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "id_kh=" + id_kh +
                ", ten_kh='" + ten_kh + '\'' +
                ", nam_sinh_kh=" + nam_sinh_kh +
                ", diem_thuong_kh=" + diem_thuong_kh +
                ", so_dien_thoai='" + so_dien_thoai + '\'' +
                ", dia_chi='" + dia_chi + '\'' +
                ", gioi_tinh='" + gioi_tinh + '\'' +
                '}';
    }
}