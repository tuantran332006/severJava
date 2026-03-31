package com.example.demo.daointerface;

import java.util.List;

public interface LoSanPhamFilterDAO<T> {

    List<T> findBySanPhamId(int idSp);

    List<T> findByPhieuNhapId(int idPhieuNhap);

    List<T> findConHang();

    List<T> findConHangBySanPhamId(int idSp);

    List<T> findBySanPhamIdOrderByExpiryAsc(int idSp);
}