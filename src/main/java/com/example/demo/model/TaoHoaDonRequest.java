package com.example.demo.model;

import java.util.List;

public class TaoHoaDonRequest {
    private HoaDon hoaDon;
    private List<ChiTietHoaDon> chiTietList;

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public List<ChiTietHoaDon> getChiTietList() {
        return chiTietList;
    }

    public void setChiTietList(List<ChiTietHoaDon> chiTietList) {
        this.chiTietList = chiTietList;
    }
}
