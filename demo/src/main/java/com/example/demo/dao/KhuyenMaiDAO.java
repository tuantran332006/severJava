package com.example.demo.dao;

import com.example.demo.daointerface.BaseDAO;
import com.example.demo.daointerface.TimeFilterDAO;
import com.example.demo.model.KhuyenMai;

public interface KhuyenMaiDAO extends BaseDAO<KhuyenMai, Integer>, TimeFilterDAO<KhuyenMai> {
}