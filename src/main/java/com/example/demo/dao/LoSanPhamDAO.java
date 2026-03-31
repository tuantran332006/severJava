package com.example.demo.dao;

import com.example.demo.daointerface.*;
import com.example.demo.model.LoSanPham;


public interface LoSanPhamDAO extends
        BaseDAO<LoSanPham, Integer>,
        TimeFilterDAO<LoSanPham>,
        ExpiryFilterDAO<LoSanPham>,
        LoSanPhamFilterDAO<LoSanPham>,
        LotStockDAO {
}