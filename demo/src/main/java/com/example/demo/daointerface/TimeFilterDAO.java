package com.example.demo.daointerface;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface TimeFilterDAO<T> {

    List<T> findByDate(LocalDate date);

    List<T> findByMonth(YearMonth yearMonth);

    List<T> findByYear(int year);
}