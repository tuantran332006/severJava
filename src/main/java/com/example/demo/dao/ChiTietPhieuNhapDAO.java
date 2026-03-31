package com.example.demo.dao;

import com.example.demo.model.ChiTietPhieuNhap;
import com.example.demo.daointerface.BaseDAO;
import com.example.demo.daointerface.StatisticTimeDAO;
import com.example.demo.daointerface.TimeFilterDAO;

import java.util.List;


public interface ChiTietPhieuNhapDAO extends
        BaseDAO<ChiTietPhieuNhap, Integer>,
        TimeFilterDAO<ChiTietPhieuNhap>,
        StatisticTimeDAO {
    
    List<ChiTietPhieuNhap> findByPhieuNhapId(int idPhieuNhap);
}
