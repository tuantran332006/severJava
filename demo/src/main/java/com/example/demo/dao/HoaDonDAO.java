package com.example.demo.dao;

import com.example.demo.daointerface.BaseDAO;
import com.example.demo.daointerface.StatisticTimeDAO;
import com.example.demo.daointerface.TimeFilterDAO;
import com.example.demo.model.HoaDon;

public interface HoaDonDAO extends BaseDAO<HoaDon, Integer>, TimeFilterDAO<HoaDon>,StatisticTimeDAO {

	
}