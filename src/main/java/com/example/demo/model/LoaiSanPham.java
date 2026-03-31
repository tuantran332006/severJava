package com.example.demo.model;

import java.util.Base64;

public class LoaiSanPham {
    private int id_loai;
    private String ten_loai;
    private byte[] hinh_anh; 

    public LoaiSanPham() {
    }

    public LoaiSanPham(int id_loai, String ten_loai, byte[] hinh_anh) {
        this.id_loai = id_loai;
        this.ten_loai = ten_loai;
        this.hinh_anh = hinh_anh;
    }

    public int getId_loai() {
        return id_loai;
    }

    public void setId_loai(int id_loai) {
        this.id_loai = id_loai;
    }

    public String getTen_loai() {
        return ten_loai;
    }

    public void setTen_loai(String ten_loai) {
        this.ten_loai = ten_loai;
    }

    public byte[] getHinh_anh() {
        return hinh_anh;
    }

    public void setHinh_anh(byte[] hinh_anh) {
        this.hinh_anh = hinh_anh;
    }

    /**
     * Hàm bổ trợ để chuyển đổi ảnh sang chuỗi Base64 
     * Rất quan trọng khi bạn gửi dữ liệu qua API dưới dạng JSON
     */
    public String getHinhAnhBase64() {
        if (hinh_anh == null || hinh_anh.length == 0) return null;
        return Base64.getEncoder().encodeToString(hinh_anh);
    }

    @Override
    public String toString() {
        return "LoaiSanPham{" +
                "id_loai=" + id_loai +
                ", ten_loai='" + ten_loai + '\'' +
                ", hinh_anh_size=" + (hinh_anh != null ? hinh_anh.length : 0) + " bytes" +
                '}';
    }
}