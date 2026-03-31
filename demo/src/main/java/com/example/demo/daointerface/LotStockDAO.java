package com.example.demo.daointerface;

public interface LotStockDAO {

    boolean updateSoLuongCon(int idLo, int soLuongConMoi);

    boolean tangSoLuongCon(int idLo, int soLuongThem);

    boolean giamSoLuongCon(int idLo, int soLuongBot);
}