package com.example.demo.daointerface;

public interface StockDAO {

    boolean updateTongSoLuong(int idSp, int tongSoLuongMoi);

    boolean tangSoLuong(int idSp, int soLuongThem);

    boolean giamSoLuong(int idSp, int soLuongBot);
}