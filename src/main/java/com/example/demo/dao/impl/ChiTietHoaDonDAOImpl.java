package com.example.demo.dao.impl;

import com.example.demo.dao.ChiTietHoaDonDAO;
import com.example.demo.model.ChiTietHoaDon;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public class ChiTietHoaDonDAOImpl implements ChiTietHoaDonDAO {

    private final JdbcTemplate jdbcTemplate;

    public ChiTietHoaDonDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class ChiTietHoaDonRowMapper implements RowMapper<ChiTietHoaDon> {
        @Override
        public ChiTietHoaDon mapRow(ResultSet rs, int rowNum) throws SQLException {
            ChiTietHoaDon ct = new ChiTietHoaDon();
            ct.setId_chi_tiet(rs.getInt("id_chi_tiet"));
            ct.setId_lo_san_pham(rs.getInt("id_lo_san_pham"));
            ct.setId_hoa_don(rs.getInt("id_hoa_don"));
            ct.setId_san_pham(rs.getInt("id_sp"));
            ct.setSo_luong(rs.getInt("so_luong"));
            ct.setDon_gia(rs.getDouble("don_gia"));
            ct.setThanh_tien(rs.getDouble("thanh_tien"));
            return ct;
        }
    }

    private final RowMapper<ChiTietHoaDon> rowMapper = new ChiTietHoaDonRowMapper();

    @Override
    public Integer insertAndReturnId(ChiTietHoaDon entity) {
        String sql = """
            INSERT INTO chi_tiet_hoa_don
            (id_lo_san_pham, id_hoa_don, id_sp, so_luong, don_gia, thanh_tien)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id_chi_tiet
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getId_lo_san_pham(),
                entity.getId_hoa_don(),
                entity.getId_san_pham(),
                entity.getSo_luong(),
                entity.getDon_gia(),
                entity.getThanh_tien()
        );
    }

    @Override
    public boolean insert(ChiTietHoaDon entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_chi_tiet(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(ChiTietHoaDon entity) {
        String sql = """
            UPDATE chi_tiet_hoa_don
            SET id_lo_san_pham = ?,
                id_hoa_don = ?,
                id_sp = ?,
                so_luong = ?,
                don_gia = ?,
                thanh_tien = ?
            WHERE id_chi_tiet = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getId_lo_san_pham(),
                entity.getId_hoa_don(),
                entity.getId_san_pham(),
                entity.getSo_luong(),
                entity.getDon_gia(),
                entity.getThanh_tien(),
                entity.getId_chi_tiet()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM chi_tiet_hoa_don WHERE id_chi_tiet = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public ChiTietHoaDon findById(Integer id) {
        String sql = "SELECT * FROM chi_tiet_hoa_don WHERE id_chi_tiet = ?";
        List<ChiTietHoaDon> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChiTietHoaDon> findAll() {
        String sql = "SELECT * FROM chi_tiet_hoa_don";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<ChiTietHoaDon> findByHoaDonId(int idHoaDon) {
        String sql = """
            SELECT *
            FROM chi_tiet_hoa_don
            WHERE id_hoa_don = ?
            ORDER BY id_chi_tiet ASC
        """;

        return jdbcTemplate.query(sql, rowMapper, idHoaDon);
    }

    @Override
    public List<ChiTietHoaDon> findByDate(LocalDate date) {
        String sql = """
            SELECT ctd.*
            FROM chi_tiet_hoa_don ctd
            JOIN hoa_don hd ON hd.id_hoa_don = ctd.id_hoa_don
            WHERE hd.ngay_lap::date = ?
        """;

        return jdbcTemplate.query(sql, rowMapper, date);
    }

    @Override
    public List<ChiTietHoaDon> findByMonth(YearMonth yearMonth) {
        String sql = """
            SELECT ctd.*
            FROM chi_tiet_hoa_don ctd
            JOIN hoa_don hd ON hd.id_hoa_don = ctd.id_hoa_don
            WHERE EXTRACT(YEAR FROM hd.ngay_lap) = ?
              AND EXTRACT(MONTH FROM hd.ngay_lap) = ?
        """;

        return jdbcTemplate.query(
                sql,
                rowMapper,
                yearMonth.getYear(),
                yearMonth.getMonthValue()
        );
    }

    @Override
    public List<ChiTietHoaDon> findByYear(int year) {
        String sql = """
            SELECT ctd.*
            FROM chi_tiet_hoa_don ctd
            JOIN hoa_don hd ON hd.id_hoa_don = ctd.id_hoa_don
            WHERE EXTRACT(YEAR FROM hd.ngay_lap) = ?
        """;

        return jdbcTemplate.query(sql, rowMapper, year);
    }

    @Override
    public double sumAmountByDate(LocalDate date) {
        String sql = """
            SELECT COALESCE(SUM(ctd.thanh_tien), 0)
            FROM chi_tiet_hoa_don ctd
            JOIN hoa_don hd ON hd.id_hoa_don = ctd.id_hoa_don
            WHERE hd.ngay_lap::date = ?
        """;
        return jdbcTemplate.queryForObject(sql, Double.class, date);
    }

    @Override
    public double sumAmountByMonth(YearMonth yearMonth) {
        String sql = """
            SELECT COALESCE(SUM(ctd.thanh_tien), 0)
            FROM chi_tiet_hoa_don ctd
            JOIN hoa_don hd ON hd.id_hoa_don = ctd.id_hoa_don
            WHERE EXTRACT(YEAR FROM hd.ngay_lap) = ?
              AND EXTRACT(MONTH FROM hd.ngay_lap) = ?
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
            SELECT COALESCE(SUM(ctd.thanh_tien), 0)
            FROM chi_tiet_hoa_don ctd
            JOIN hoa_don hd ON hd.id_hoa_don = ctd.id_hoa_don
            WHERE EXTRACT(YEAR FROM hd.ngay_lap) = ?
        """;
        return jdbcTemplate.queryForObject(sql, Double.class, year);
    }

    @Override
    public int sumQuantityByDate(LocalDate date) {
        String sql = """
            SELECT COALESCE(SUM(ctd.so_luong), 0)
            FROM chi_tiet_hoa_don ctd
            JOIN hoa_don hd ON hd.id_hoa_don = ctd.id_hoa_don
            WHERE hd.ngay_lap::date = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, date);
    }

    @Override
    public int sumQuantityByMonth(YearMonth yearMonth) {
        String sql = """
            SELECT COALESCE(SUM(ctd.so_luong), 0)
            FROM chi_tiet_hoa_don ctd
            JOIN hoa_don hd ON hd.id_hoa_don = ctd.id_hoa_don
            WHERE EXTRACT(YEAR FROM hd.ngay_lap) = ?
              AND EXTRACT(MONTH FROM hd.ngay_lap) = ?
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
            SELECT COALESCE(SUM(ctd.so_luong), 0)
            FROM chi_tiet_hoa_don ctd
            JOIN hoa_don hd ON hd.id_hoa_don = ctd.id_hoa_don
            WHERE EXTRACT(YEAR FROM hd.ngay_lap) = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, year);
    }
}
