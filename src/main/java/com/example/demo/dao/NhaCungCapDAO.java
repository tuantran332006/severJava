package com.example.demo.dao;

import com.example.demo.daointerface.BaseDAO;
import com.example.demo.daointerface.TimeFilterDAO;
import com.example.demo.model.NhaCungCap;

public interface NhaCungCapDAO extends BaseDAO<NhaCungCap, Integer>, TimeFilterDAO<NhaCungCap> {
}