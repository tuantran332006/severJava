package com.example.demo.service;

import com.example.demo.dao.KhachHangDAO;
import com.example.demo.model.KhachHang;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangService {

    private final KhachHangDAO khachHangDAO;

    // ✅ CHUẨN: constructor injection
    public KhachHangService(KhachHangDAO khachHangDAO) {
        this.khachHangDAO = khachHangDAO;
    }

    public boolean themKhachHang(KhachHang khachHang) {
        return khachHangDAO.insert(khachHang);
    }

    public boolean suaKhachHang(KhachHang khachHang) {
        return khachHangDAO.update(khachHang);
    }

    public boolean xoaKhachHang(int idKhachHang) {
        return khachHangDAO.delete(idKhachHang);
    }

    public KhachHang timKhachHangTheoId(int idKhachHang) {
        return khachHangDAO.findById(idKhachHang);
    }

    public List<KhachHang> layTatCaKhachHang() {
        return khachHangDAO.findAll();
    }
}