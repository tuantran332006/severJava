package com.example.demo.dao.impl;

import com.example.demo.dao.ChiTietPhieuNhapDAO;
import com.example.demo.model.ChiTietPhieuNhap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public class ChiTietPhieuNhapDAOImpl implements ChiTietPhieuNhapDAO {

    private final JdbcTemplate jdbcTemplate;

    public ChiTietPhieuNhapDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class ChiTietPhieuNhapRowMapper implements RowMapper<ChiTietPhieuNhap> {
        @Override
        public ChiTietPhieuNhap mapRow(ResultSet rs, int rowNum) throws SQLException {
            ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
            ct.setId_chi_tiet_nhap(rs.getInt("id_chi_tiet_nhap"));
            ct.setId_phieu_nhap(rs.getInt("id_phieu_nhap"));
            ct.setId_sp(rs.getInt("id_sp"));
            ct.setSo_luong(rs.getInt("so_luong"));
            ct.setDon_gia_nhap(rs.getDouble("don_gia_nhap"));
            ct.setThanh_tien(rs.getDouble("thanh_tien"));

            if (rs.getTimestamp("ngay_nhap") != null) {
                ct.setNgay_nhap(rs.getTimestamp("ngay_nhap").toLocalDateTime());
            }
            return ct;
        }
    }

    private final RowMapper<ChiTietPhieuNhap> rowMapper = new ChiTietPhieuNhapRowMapper();

    // ========================== INSERT ==========================

    @Override
    public Integer insertAndReturnId(ChiTietPhieuNhap entity) {
        String sql = """
            INSERT INTO chi_tiet_phieu_nhap
            (id_phieu_nhap, id_sp, so_luong, don_gia_nhap, thanh_tien, ngay_nhap)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id_chi_tiet_nhap
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getId_phieu_nhap(),
                entity.getId_sp(),
                entity.getSo_luong(),
                entity.getDon_gia_nhap(),
                entity.getThanh_tien(),
                entity.getNgay_nhap()
        );
    }

    @Override
    public boolean insert(ChiTietPhieuNhap entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_chi_tiet_nhap(id);
            return true;
        }
        return false;
    }

    // ========================== UPDATE / DELETE ==========================

    @Override
    public boolean update(ChiTietPhieuNhap entity) {
        String sql = """
            UPDATE chi_tiet_phieu_nhap
            SET id_phieu_nhap = ?, id_sp = ?, so_luong = ?,
                don_gia_nhap = ?, thanh_tien = ?, ngay_nhap = ?
            WHERE id_chi_tiet_nhap = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getId_phieu_nhap(),
                entity.getId_sp(),
                entity.getSo_luong(),
                entity.getDon_gia_nhap(),
                entity.getThanh_tien(),
                entity.getNgay_nhap(),
                entity.getId_chi_tiet_nhap()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM chi_tiet_phieu_nhap WHERE id_chi_tiet_nhap = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    // ========================== QUERY ==========================

    @Override
    public ChiTietPhieuNhap findById(Integer id) {
        String sql = "SELECT * FROM chi_tiet_phieu_nhap WHERE id_chi_tiet_nhap = ?";
        List<ChiTietPhieuNhap> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChiTietPhieuNhap> findAll() {
        String sql = "SELECT * FROM chi_tiet_phieu_nhap";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // ========================== TIME FILTER ==========================

    @Override
    public List<ChiTietPhieuNhap> findByDate(LocalDate date) {
        String sql = """
            SELECT * FROM chi_tiet_phieu_nhap
            WHERE ngay_nhap::date = ?
        """;
        return jdbcTemplate.query(sql, rowMapper, date);
    }

    @Override
    public List<ChiTietPhieuNhap> findByMonth(YearMonth yearMonth) {
        String sql = """
            SELECT * FROM chi_tiet_phieu_nhap
            WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
              AND EXTRACT(MONTH FROM ngay_nhap) = ?
        """;
        return jdbcTemplate.query(
                sql,
                rowMapper,
                yearMonth.getYear(),
                yearMonth.getMonthValue()
        );
    }

    @Override
    public List<ChiTietPhieuNhap> findByYear(int year) {
        String sql = """
            SELECT * FROM chi_tiet_phieu_nhap
            WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
        """;
        return jdbcTemplate.query(sql, rowMapper, year);
    }

    // ========================== STATS ==========================

    @Override
    public double sumAmountByDate(LocalDate date) {
        String sql = """
            SELECT COALESCE(SUM(thanh_tien), 0)
            FROM chi_tiet_phieu_nhap
            WHERE ngay_nhap::date = ?
        """;
        return jdbcTemplate.queryForObject(sql, Double.class, date);
    }

    @Override
    public double sumAmountByMonth(YearMonth yearMonth) {
        String sql = """
            SELECT COALESCE(SUM(thanh_tien), 0)
            FROM chi_tiet_phieu_nhap
            WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
              AND EXTRACT(MONTH FROM ngay_nhap) = ?
        """;
        return jdbcTemplate.queryForObject(
                sql,
                Double.class,
                yearMonth.getYear(),
                yearMonth.getMonthValue()
        );
    }

    @Override
    public double sumAmountByYear(int year) {
        String sql = """
            SELECT COALESCE(SUM(thanh_tien), 0)
            FROM chi_tiet_phieu_nhap
            WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
        """;
        return jdbcTemplate.queryForObject(sql, Double.class, year);
    }

    @Override
    public int sumQuantityByDate(LocalDate date) {
        String sql = """
            SELECT COALESCE(SUM(so_luong), 0)
            FROM chi_tiet_phieu_nhap
            WHERE ngay_nhap::date = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, date);
    }

    @Override
    public int sumQuantityByMonth(YearMonth yearMonth) {
        String sql = """
            SELECT COALESCE(SUM(so_luong), 0)
            FROM chi_tiet_phieu_nhap
            WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
              AND EXTRACT(MONTH FROM ngay_nhap) = ?
        """;
        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                yearMonth.getYear(),
                yearMonth.getMonthValue()
        );
    }

    @Override
    public int sumQuantityByYear(int year) {
        String sql = """
            SELECT COALESCE(SUM(so_luong), 0)
            FROM chi_tiet_phieu_nhap
            WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, year);
    }

    @Override
    public List<ChiTietPhieuNhap> findByPhieuNhapId(int idPhieuNhap) {
        String sql = "SELECT * FROM chi_tiet_phieu_nhap WHERE id_phieu_nhap = ?";
        return jdbcTemplate.query(sql, rowMapper, idPhieuNhap);
    }
}