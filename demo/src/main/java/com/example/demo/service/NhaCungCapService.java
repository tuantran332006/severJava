package com.example.demo.service;

import com.example.demo.dao.NhaCungCapDAO;
import com.example.demo.model.NhaCungCap;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class NhaCungCapService {

    private final NhaCungCapDAO nhaCungCapDAO;

    // ✅ Constructor injection – CHUẨN SPRING BOOT
    public NhaCungCapService(NhaCungCapDAO nhaCungCapDAO) {
        this.nhaCungCapDAO = nhaCungCapDAO;
    }

    // ========================== CRUD ==========================

    public boolean themNhaCungCap(NhaCungCap ncc) {
        return nhaCungCapDAO.insert(ncc);
    }

    public boolean capNhatNhaCungCap(NhaCungCap ncc) {
        return nhaCungCapDAO.update(ncc);
    }

    public boolean xoaNhaCungCap(int idNcc) {
        return nhaCungCapDAO.delete(idNcc);
    }

    public NhaCungCap timTheoId(int idNcc) {
        return nhaCungCapDAO.findById(idNcc);
    }

    public List<NhaCungCap> layTatCa() {
        return nhaCungCapDAO.findAll();
    }

    // ========================== TÌM KIẾM THEO THỜI GIAN ==========================

    public List<NhaCungCap> timTheoNgayHopTac(LocalDate date) {
        return nhaCungCapDAO.findByDate(date);
    }

    public List<NhaCungCap> timTheoThangHopTac(YearMonth yearMonth) {
        return nhaCungCapDAO.findByMonth(yearMonth);
    }

    public List<NhaCungCap> timTheoNamHopTac(int year) {
        return nhaCungCapDAO.findByYear(year);
    }
}