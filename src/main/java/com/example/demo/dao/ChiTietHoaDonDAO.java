package com.example.demo.dao;

import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.daointerface.BaseDAO;
import com.example.demo.daointerface.StatisticTimeDAO;
import com.example.demo.daointerface.TimeFilterDAO;


public interface ChiTietHoaDonDAO extends BaseDAO<ChiTietHoaDon, Integer>, TimeFilterDAO<ChiTietHoaDon>,StatisticTimeDAO {
}