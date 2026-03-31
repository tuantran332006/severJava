package com.example.demo.daointerface;

import java.util.List;

public interface SanPhamFilterDAO<T> {

    List<T> findByTen(String tenSp);

    List<T> findByLoai(int idLoai);

    List<T> findByKhoangGia(double giaMin, double giaMax);
}