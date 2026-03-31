package com.example.demo.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class KhuyenMai {
    private int id_khuyen_mai;
    private String ten_khuyen_mai;
    private double phan_tram_giam;
    private LocalDateTime ngay_bat_dau;
    private LocalDateTime ngay_ket_thuc; 
    private String mo_ta;

    public KhuyenMai() {
    }

    public KhuyenMai(int id_khuyen_mai, String ten_khuyen_mai, double phan_tram_giam, 
                    LocalDateTime ngay_bat_dau, LocalDateTime ngay_ket_thuc, String mo_ta) {
        this.id_khuyen_mai = id_khuyen_mai;
        this.ten_khuyen_mai = ten_khuyen_mai;
        this.phan_tram_giam = phan_tram_giam;
        setNgay_bat_dau(ngay_bat_dau);
        setNgay_ket_thuc(ngay_ket_thuc);
        this.mo_ta = mo_ta;
    }

    // Getter & Setter cho ngay_bat_dau
    public LocalDateTime getNgay_bat_dau() {
        return ngay_bat_dau;
    }

    public void setNgay_bat_dau(LocalDateTime ngay_bat_dau) {
        if (ngay_bat_dau != null) {
            this.ngay_bat_dau = ngay_bat_dau.truncatedTo(ChronoUnit.SECONDS);
        } else {
            this.ngay_bat_dau = null;
        }
    }

    // Getter & Setter cho ngay_ket_thuc
    public LocalDateTime getNgay_ket_thuc() {
        return ngay_ket_thuc;
    }

    public void setNgay_ket_thuc(LocalDateTime ngay_ket_thuc) {
        if (ngay_ket_thuc != null) {
            this.ngay_ket_thuc = ngay_ket_thuc.truncatedTo(ChronoUnit.SECONDS);
        } else {
            this.ngay_ket_thuc = null;
        }
    }

    // Hàm kiểm tra khuyến mãi có đang hiệu lực hay không
    public boolean dangTrongThoiGianKhuyenMai() {
        LocalDateTime bayGio = LocalDateTime.now();
        return (bayGio.isAfter(ngay_bat_dau) || bayGio.isEqual(ngay_bat_dau)) 
            && (bayGio.isBefore(ngay_ket_thuc) || bayGio.isEqual(ngay_ket_thuc));
    }

    // Hàm định dạng ngày để hiển thị lên giao diện
    public String getNgayBatDauFormatted() {
        if (ngay_bat_dau == null) return "";
        return ngay_bat_dau.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getNgayKetThucFormatted() {
        if (ngay_ket_thuc == null) return "";
        return ngay_ket_thuc.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    // --- Các Getter và Setter khác ---
    public int getId_khuyen_mai() { return id_khuyen_mai; }
    public void setId_khuyen_mai(int id_khuyen_mai) { this.id_khuyen_mai = id_khuyen_mai; }

    public String getTen_khuyen_mai() { return ten_khuyen_mai; }
    public void setTen_khuyen_mai(String ten_khuyen_mai) { this.ten_khuyen_mai = ten_khuyen_mai; }

    public double getPhan_tram_giam() { return phan_tram_giam; }
    public void setPhan_tram_giam(double phan_tram_giam) { this.phan_tram_giam = phan_tram_giam; }

    public String getMo_ta() { return mo_ta; }
    public void setMo_ta(String mo_ta) { this.mo_ta = mo_ta; }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "ten='" + ten_khuyen_mai + '\'' +
                ", giam=" + phan_tram_giam + "%" +
                ", tu=" + getNgayBatDauFormatted() +
                ", den=" + getNgayKetThucFormatted() +
                '}';
    }
}