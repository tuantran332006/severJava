package com.example.demo.dao;

import com.example.demo.daointerface.BaseDAO;
import com.example.demo.daointerface.TimeFilterDAO;
import com.example.demo.model.NhanVien;

public interface NhanVienDAO extends BaseDAO<NhanVien, Integer>, TimeFilterDAO<NhanVien> {
}