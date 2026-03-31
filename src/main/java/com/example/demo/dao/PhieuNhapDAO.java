package com.example.demo.dao;

import com.example.demo.daointerface.BaseDAO;
import com.example.demo.daointerface.StatisticTimeDAO;
import com.example.demo.daointerface.TimeFilterDAO;
import com.example.demo.model.PhieuNhap;

public interface PhieuNhapDAO extends BaseDAO<PhieuNhap, Integer>, TimeFilterDAO<PhieuNhap>,StatisticTimeDAO {
}