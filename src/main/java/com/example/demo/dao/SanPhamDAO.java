package com.example.demo.dao;


import com.example.demo.daointerface.BaseDAO;
import com.example.demo.daointerface.SanPhamFilterDAO;
import com.example.demo.daointerface.StockDAO;
import com.example.demo.model.SanPham;


public interface SanPhamDAO extends
        BaseDAO<SanPham, Integer>,
        SanPhamFilterDAO<SanPham>,
        StockDAO {
	boolean existsById(int idSp);

}