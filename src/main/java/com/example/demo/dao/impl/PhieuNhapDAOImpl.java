package com.example.demo.dao.impl;

import com.example.demo.dao.PhieuNhapDAO;
import com.example.demo.daointerface.StatisticTimeDAO;
import com.example.demo.model.PhieuNhap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public class PhieuNhapDAOImpl implements PhieuNhapDAO, StatisticTimeDAO {

    private final JdbcTemplate jdbcTemplate;

    public PhieuNhapDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========================== ROW MAPPER ==========================

    private static class PhieuNhapRowMapper implements RowMapper<PhieuNhap> {
        @Override
        public PhieuNhap mapRow(ResultSet rs, int rowNum) throws SQLException {
            PhieuNhap pn = new PhieuNhap();
            pn.setId_phieu_nhap(rs.getInt("id_phieu_nhap"));
            pn.setId_ncc(rs.getInt("id_ncc"));
            pn.setId_nhan_vien(rs.getInt("id_nhan_vien"));

            if (rs.getTimestamp("ngay_nhap") != null) {
                pn.setNgay_nhap(rs.getTimestamp("ngay_nhap").toLocalDateTime());
            }

            pn.setTong_tien(rs.getDouble("tong_tien"));
            pn.setGhi_chu(rs.getString("ghi_chu"));
            return pn;
        }
    }

    private final RowMapper<PhieuNhap> rowMapper = new PhieuNhapRowMapper();

    // ========================== INSERT ==========================

    @Override
    public Integer insertAndReturnId(PhieuNhap entity) {
        String sql = """
            INSERT INTO phieu_nhap
            (id_ncc, id_nhan_vien, ngay_nhap, tong_tien, ghi_chu)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id_phieu_nhap
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getId_ncc(),
                entity.getId_nhan_vien(),
                entity.getNgay_nhap(),
                entity.getTong_tien(),
                entity.getGhi_chu()
        );
    }

    @Override
    public boolean insert(PhieuNhap entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_phieu_nhap(id);
            return true;
        }
        return false;
    }

    // ========================== UPDATE / DELETE ==========================

    @Override
    public boolean update(PhieuNhap entity) {
        String sql = """
            UPDATE phieu_nhap
            SET id_ncc = ?, id_nhan_vien = ?, ngay_nhap = ?, tong_tien = ?, ghi_chu = ?
            WHERE id_phieu_nhap = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getId_ncc(),
                entity.getId_nhan_vien(),
                entity.getNgay_nhap(),
                entity.getTong_tien(),
                entity.getGhi_chu(),
                entity.getId_phieu_nhap()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update(
                "DELETE FROM phieu_nhap WHERE id_phieu_nhap = ?",
                id
        ) > 0;
    }

    // ========================== QUERY ==========================

    @Override
    public PhieuNhap findById(Integer id) {
        List<PhieuNhap> list = jdbcTemplate.query(
                "SELECT * FROM phieu_nhap WHERE id_phieu_nhap = ?",
                rowMapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<PhieuNhap> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM phieu_nhap ORDER BY ngay_nhap DESC",
                rowMapper
        );
    }

    @Override
    public List<PhieuNhap> findByDate(LocalDate date) {
        return jdbcTemplate.query(
                "SELECT * FROM phieu_nhap WHERE ngay_nhap::date = ? ORDER BY ngay_nhap DESC",
                rowMapper,
                date
        );
    }

    @Override
    public List<PhieuNhap> findByMonth(YearMonth ym) {
        return jdbcTemplate.query(
                """
                SELECT * FROM phieu_nhap
                WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
                  AND EXTRACT(MONTH FROM ngay_nhap) = ?
                ORDER BY ngay_nhap DESC
                """,
                rowMapper,
                ym.getYear(),
                ym.getMonthValue()
        );
    }

    @Override
    public List<PhieuNhap> findByYear(int year) {
        return jdbcTemplate.query(
                """
                SELECT * FROM phieu_nhap
                WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
                ORDER BY ngay_nhap DESC
                """,
                rowMapper,
                year
        );
    }

    // ========================== THỐNG KÊ ==========================

    @Override
    public double sumAmountByDate(LocalDate date) {
        return jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(tong_tien), 0) FROM phieu_nhap WHERE ngay_nhap::date = ?",
                Double.class,
                date
        );
    }

    @Override
    public double sumAmountByMonth(YearMonth ym) {
        return jdbcTemplate.queryForObject(
                """
                SELECT COALESCE(SUM(tong_tien), 0)
                FROM phieu_nhap
                WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
                  AND EXTRACT(MONTH FROM ngay_nhap) = ?
                """,
                Double.class,
                ym.getYear(),
                ym.getMonthValue()
        );
    }

    @Override
    public double sumAmountByYear(int year) {
        return jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(tong_tien), 0) FROM phieu_nhap WHERE EXTRACT(YEAR FROM ngay_nhap) = ?",
                Double.class,
                year
        );
    }

    @Override
    public int sumQuantityByDate(LocalDate date) {
        String sql = """
            SELECT COALESCE(SUM(ctpn.so_luong), 0)
            FROM phieu_nhap pn
            JOIN chi_tiet_phieu_nhap ctpn ON pn.id_phieu_nhap = ctpn.id_phieu_nhap
            WHERE pn.ngay_nhap::date = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, date);
    }

    @Override
    public int sumQuantityByMonth(YearMonth ym) {
        String sql = """
            SELECT COALESCE(SUM(ctpn.so_luong), 0)
            FROM phieu_nhap pn
            JOIN chi_tiet_phieu_nhap ctpn ON pn.id_phieu_nhap = ctpn.id_phieu_nhap
            WHERE EXTRACT(YEAR FROM pn.ngay_nhap) = ?
              AND EXTRACT(MONTH FROM pn.ngay_nhap) = ?
        """;
        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                ym.getYear(),
                ym.getMonthValue()
        );
    }

    @Override
    public int sumQuantityByYear(int year) {
        String sql = """
            SELECT COALESCE(SUM(ctpn.so_luong), 0)
            FROM phieu_nhap pn
            JOIN chi_tiet_phieu_nhap ctpn ON pn.id_phieu_nhap = ctpn.id_phieu_nhap
            WHERE EXTRACT(YEAR FROM pn.ngay_nhap) = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, year);
    }
}