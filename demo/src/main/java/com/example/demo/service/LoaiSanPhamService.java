package com.example.demo.service;

import com.example.demo.dao.LoaiSanPhamDAO;
import com.example.demo.model.LoaiSanPham;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiSanPhamService {

    private final LoaiSanPhamDAO loaiSanPhamDAO;

    // ✅ CHUẨN SPRING BOOT: constructor injection
    public LoaiSanPhamService(LoaiSanPhamDAO loaiSanPhamDAO) {
        this.loaiSanPhamDAO = loaiSanPhamDAO;
    }

    public boolean themLoaiSanPham(LoaiSanPham loaiSanPham) {
        return loaiSanPhamDAO.insert(loaiSanPham);
    }

    public boolean suaLoaiSanPham(LoaiSanPham loaiSanPham) {
        return loaiSanPhamDAO.update(loaiSanPham);
    }

    public boolean xoaLoaiSanPham(int idLoai) {
        return loaiSanPhamDAO.delete(idLoai);
    }

    public LoaiSanPham timLoaiSanPhamTheoId(int idLoai) {
        return loaiSanPhamDAO.findById(idLoai);
    }

    public List<LoaiSanPham> layTatCaLoaiSanPham() {
        return loaiSanPhamDAO.findAll();
    }
}
