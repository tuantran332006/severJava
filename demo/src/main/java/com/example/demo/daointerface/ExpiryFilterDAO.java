package com.example.demo.daointerface;

import java.time.LocalDate;
import java.util.List;

public interface ExpiryFilterDAO<T> {

    List<T> findByExpiryDate(LocalDate date);
    List<T> findByExpiryBetween(LocalDate startDate, LocalDate endDate);
    List<T> findExpiringWithinDays(int days);
    List<T> findExpired();
}