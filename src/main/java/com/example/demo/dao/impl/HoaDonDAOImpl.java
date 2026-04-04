package com.example.demo.dao.impl;

import com.example.demo.dao.HoaDonDAO;
import com.example.demo.daointerface.StatisticTimeDAO;
import com.example.demo.model.HoaDon;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public class HoaDonDAOImpl implements HoaDonDAO, StatisticTimeDAO {

    private final JdbcTemplate jdbcTemplate;

    public HoaDonDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========================== ROW MAPPER ==========================
    private static class HoaDonRowMapper implements RowMapper<HoaDon> {
        @Override
        public HoaDon mapRow(ResultSet rs, int rowNum) throws SQLException {
            HoaDon hd = new HoaDon();

            hd.setId_hoa_don(rs.getInt("id_hoa_don"));
            hd.setId_kh(rs.getObject("id_kh", Integer.class));
            hd.setId_nhanVien(rs.getObject("id_nhan_vien", Integer.class));
            hd.setId_khuyenMai(rs.getObject("id_khuyen_mai", Integer.class));

            if (rs.getDate("ngay_lap") != null) {
                hd.setNgay_lap(rs.getDate("ngay_lap").toLocalDate());
            }

            hd.setTong_tien(rs.getDouble("tong_tien"));
            hd.setGhi_chu(rs.getString("ghi_chu"));

            return hd;
        }
    }

    private final RowMapper<HoaDon> rowMapper = new HoaDonRowMapper();

    // ========================== INSERT ==========================
    @Override
    public Integer insertAndReturnId(HoaDon entity) {
        String sql = """
            INSERT INTO hoa_don
            (id_kh, id_nhan_vien, id_khuyen_mai, ngay_lap, tong_tien, ghi_chu)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id_hoa_don
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getId_kh(),
                entity.getId_nhanVien(),
                entity.getId_khuyenMai(),
                entity.getNgay_lap(),
                entity.getTong_tien(),
                entity.getGhi_chu()
        );
    }

    @Override
    public boolean insert(HoaDon entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_hoa_don(id);
            return true;
        }
        return false;
    }

    // ========================== UPDATE / DELETE ==========================
    @Override
    public boolean update(HoaDon entity) {
        String sql = """
            UPDATE hoa_don
            SET id_kh = ?,
                id_nhan_vien = ?,
                id_khuyen_mai = ?,
                ngay_lap = ?,
                tong_tien = ?,
                ghi_chu = ?
            WHERE id_hoa_don = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getId_kh(),
                entity.getId_nhanVien(),
                entity.getId_khuyenMai(),
                entity.getNgay_lap(),
                entity.getTong_tien(),
                entity.getGhi_chu(),
                entity.getId_hoa_don()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM hoa_don WHERE id_hoa_don = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    // ========================== QUERY ==========================
    @Override
    public HoaDon findById(Integer id) {
        String sql = "SELECT * FROM hoa_don WHERE id_hoa_don = ?";
        List<HoaDon> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<HoaDon> findAll() {
        String sql = "SELECT * FROM hoa_don ORDER BY ngay_lap DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<HoaDon> findByDate(LocalDate date) {
        String sql = """
            SELECT *
            FROM hoa_don
            WHERE ngay_lap::date = ?
            ORDER BY ngay_lap DESC
        """;
        return jdbcTemplate.query(sql, rowMapper, date);
    }

    @Override
    public List<HoaDon> findByMonth(YearMonth ym) {
        String sql = """
            SELECT *
            FROM hoa_don
            WHERE EXTRACT(YEAR FROM ngay_lap) = ?
              AND EXTRACT(MONTH FROM ngay_lap) = ?
            ORDER BY ngay_lap DESC
        """;
        return jdbcTemplate.query(sql, rowMapper, ym.getYear(), ym.getMonthValue());
    }

    @Override
    public List<HoaDon> findByYear(int year) {
        String sql = """
            SELECT *
            FROM hoa_don
            WHERE EXTRACT(YEAR FROM ngay_lap) = ?
            ORDER BY ngay_lap DESC
        """;
        return jdbcTemplate.query(sql, rowMapper, year);
    }

    // ========================== THỐNG KÊ ==========================
    @Override
    public double sumAmountByDate(LocalDate date) {
        String sql = """
            SELECT COALESCE(SUM(tong_tien), 0)
            FROM hoa_don
            WHERE ngay_lap::date = ?
        """;
        return jdbcTemplate.queryForObject(sql, Double.class, date);
    }

    @Override
    public double sumAmountByMonth(YearMonth ym) {
        String sql = """
            SELECT COALESCE(SUM(tong_tien), 0)
            FROM hoa_don
            WHERE EXTRACT(YEAR FROM ngay_lap) = ?
              AND EXTRACT(MONTH FROM ngay_lap) = ?
        """;
        return jdbcTemplate.queryForObject(sql, Double.class, ym.getYear(), ym.getMonthValue());
    }

    @Override
    public double sumAmountByYear(int year) {
        String sql = """
            SELECT COALESCE(SUM(tong_tien), 0)
            FROM hoa_don
            WHERE EXTRACT(YEAR FROM ngay_lap) = ?
        """;
        return jdbcTemplate.queryForObject(sql, Double.class, year);
    }

    @Override
    public int sumQuantityByDate(LocalDate date) {
        String sql = """
            SELECT COALESCE(SUM(ct.so_luong), 0)
            FROM hoa_don hd
            JOIN chi_tiet_hoa_don ct ON hd.id_hoa_don = ct.id_hoa_don
            WHERE hd.ngay_lap::date = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, date);
    }

    @Override
    public int sumQuantityByMonth(YearMonth ym) {
        String sql = """
            SELECT COALESCE(SUM(ct.so_luong), 0)
            FROM hoa_don hd
            JOIN chi_tiet_hoa_don ct ON hd.id_hoa_don = ct.id_hoa_don
            WHERE EXTRACT(YEAR FROM hd.ngay_lap) = ?
              AND EXTRACT(MONTH FROM hd.ngay_lap) = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, ym.getYear(), ym.getMonthValue());
    }

    @Override
    public int sumQuantityByYear(int year) {
        String sql = """
            SELECT COALESCE(SUM(ct.so_luong), 0)
            FROM hoa_don hd
            JOIN chi_tiet_hoa_don ct ON hd.id_hoa_don = ct.id_hoa_don
            WHERE EXTRACT(YEAR FROM hd.ngay_lap) = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, year);
    }
}
