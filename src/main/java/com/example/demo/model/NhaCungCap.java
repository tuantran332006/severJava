package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class NhaCungCap {
    private int id_ncc;
    private String ten_ncc;
    private String so_dien_thoai;
    private String dia_chi;
    private String email;
    private LocalDateTime ngay_hop_tac;

    public NhaCungCap() {
    }

    public NhaCungCap(int id_ncc, String ten_ncc, String so_dien_thoai, String dia_chi, String email, LocalDateTime ngay_hop_tac) {
        this.id_ncc = id_ncc;
        this.ten_ncc = ten_ncc;
        this.so_dien_thoai = so_dien_thoai;
        this.dia_chi = dia_chi;
        this.email = email;
        setNgay_hop_tac(ngay_hop_tac);
    }

    // Getter và Setter cho ngay_hop_tac
    public LocalDateTime getNgay_hop_tac() {
        return ngay_hop_tac;
    }

    public void setNgay_hop_tac(LocalDateTime ngay_hop_tac) {
        if (ngay_hop_tac != null) {
            // Cắt bỏ phần nano giây để đồng bộ với các class khác và Database
            this.ngay_hop_tac = ngay_hop_tac.truncatedTo(ChronoUnit.SECONDS);
        } else {
            this.ngay_hop_tac = null;
        }
    }

    // Hàm bổ trợ hiển thị ngày tháng trên giao diện hoặc báo cáo
    public String getNgayHopTacFormatted() {
        if (this.ngay_hop_tac == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.ngay_hop_tac.format(formatter);
    }

    // --- Các Getter và Setter cho các thuộc tính còn lại ---
    public int getId_ncc() { return id_ncc; }
    public void setId_ncc(int id_ncc) { this.id_ncc = id_ncc; }

    public String getTen_ncc() { return ten_ncc; }
    public void setTen_ncc(String ten_ncc) { this.ten_ncc = ten_ncc; }

    public String getSo_dien_thoai() { return so_dien_thoai; }
    public void setSo_dien_thoai(String so_dien_thoai) { this.so_dien_thoai = so_dien_thoai; }

    public String getDia_chi() { return dia_chi; }
    public void setDia_chi(String dia_chi) { this.dia_chi = dia_chi; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "NhaCungCap{" +
                "id_ncc=" + id_ncc +
                ", ten_ncc='" + ten_ncc + '\'' +
                ", ngay_hop_tac=" + getNgayHopTacFormatted() +
                '}';
    }

}