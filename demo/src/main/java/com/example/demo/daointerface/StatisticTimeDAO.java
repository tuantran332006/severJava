package com.example.demo.daointerface;

import java.time.LocalDate;
import java.time.YearMonth;

public interface StatisticTimeDAO {

    // ===== TỔNG TIỀN =====
    double sumAmountByDate(LocalDate date);

    double sumAmountByMonth(YearMonth yearMonth);

    double sumAmountByYear(int year);

    // ===== TỔNG SỐ LƯỢNG =====
    int sumQuantityByDate(LocalDate date);

    int sumQuantityByMonth(YearMonth yearMonth);

    int sumQuantityByYear(int year);
}