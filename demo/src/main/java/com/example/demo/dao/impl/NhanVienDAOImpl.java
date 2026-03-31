package com.example.demo.dao.impl;

import com.example.demo.dao.NhanVienDAO;
import com.example.demo.model.NhanVien;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public class NhanVienDAOImpl implements NhanVienDAO {

    private final JdbcTemplate jdbcTemplate;

    public NhanVienDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========================== ROW MAPPER ==========================

    private static class NhanVienRowMapper implements RowMapper<NhanVien> {
        @Override
        public NhanVien mapRow(ResultSet rs, int rowNum) throws SQLException {
            NhanVien nv = new NhanVien();
            nv.setId_nhan_vien(rs.getInt("id_nhan_vien"));
            nv.setHo_ten(rs.getString("ho_ten"));
            nv.setGioi_tinh(rs.getString("gioi_tinh"));
            nv.setTuoi(rs.getInt("tuoi"));
            nv.setLuong(rs.getDouble("luong"));
            nv.setThoi_gian_gan_bo(rs.getInt("thoi_gian_gan_bo"));
            nv.setDiem_thuong(rs.getInt("diem_thuong"));
            nv.setDiem_danh(rs.getInt("diem_danh"));
            nv.setSo_dien_thoai(rs.getString("so_dien_thoai"));
            nv.setDia_chi(rs.getString("dia_chi"));
            nv.setChuc_vu(rs.getString("chuc_vu"));

            if (rs.getTimestamp("ngay_vao_lam") != null) {
                nv.setNgay_vao_lam(rs.getTimestamp("ngay_vao_lam").toLocalDateTime());
            }
            return nv;
        }
    }

    private final RowMapper<NhanVien> rowMapper = new NhanVienRowMapper();

    // ========================== INSERT ==========================

    @Override
    public Integer insertAndReturnId(NhanVien entity) {
        String sql = """
            INSERT INTO nhan_vien
            (ho_ten, gioi_tinh, tuoi, luong, thoi_gian_gan_bo,
             diem_thuong, diem_danh, so_dien_thoai, dia_chi, chuc_vu, ngay_vao_lam)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id_nhan_vien
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getHo_ten(),
                entity.getGioi_tinh(),
                entity.getTuoi(),
                entity.getLuong(),
                entity.getThoi_gian_gan_bo(),
                entity.getDiem_thuong(),
                entity.getDiem_danh(),
                entity.getSo_dien_thoai(),
                entity.getDia_chi(),
                entity.getChuc_vu(),
                entity.getNgay_vao_lam()
        );
    }

    @Override
    public boolean insert(NhanVien entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_nhan_vien(id);
            return true;
        }
        return false;
    }

    // ========================== UPDATE / DELETE ==========================

    @Override
    public boolean update(NhanVien entity) {
        String sql = """
            UPDATE nhan_vien
            SET ho_ten = ?, gioi_tinh = ?, tuoi = ?, luong = ?, thoi_gian_gan_bo = ?,
                diem_thuong = ?, diem_danh = ?, so_dien_thoai = ?, dia_chi = ?,
                chuc_vu = ?, ngay_vao_lam = ?
            WHERE id_nhan_vien = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getHo_ten(),
                entity.getGioi_tinh(),
                entity.getTuoi(),
                entity.getLuong(),
                entity.getThoi_gian_gan_bo(),
                entity.getDiem_thuong(),
                entity.getDiem_danh(),
                entity.getSo_dien_thoai(),
                entity.getDia_chi(),
                entity.getChuc_vu(),
                entity.getNgay_vao_lam(),
                entity.getId_nhan_vien()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update(
                "DELETE FROM nhan_vien WHERE id_nhan_vien = ?",
                id
        ) > 0;
    }

    // ========================== QUERY ==========================

    @Override
    public NhanVien findById(Integer id) {
        List<NhanVien> list = jdbcTemplate.query(
                "SELECT * FROM nhan_vien WHERE id_nhan_vien = ?",
                rowMapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<NhanVien> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM nhan_vien ORDER BY id_nhan_vien DESC",
                rowMapper
        );
    }

    // ========================== LỌC THEO THỜI GIAN ==========================

    @Override
    public List<NhanVien> findByDate(LocalDate date) {
        return jdbcTemplate.query(
                "SELECT * FROM nhan_vien WHERE ngay_vao_lam::date = ?",
                rowMapper,
                date
        );
    }

    @Override
    public List<NhanVien> findByMonth(YearMonth ym) {
        return jdbcTemplate.query(
                """
                SELECT * FROM nhan_vien
                WHERE EXTRACT(YEAR FROM ngay_vao_lam) = ?
                  AND EXTRACT(MONTH FROM ngay_vao_lam) = ?
                """,
                rowMapper,
                ym.getYear(),
                ym.getMonthValue()
        );
    }

    @Override
    public List<NhanVien> findByYear(int year) {
        return jdbcTemplate.query(
                "SELECT * FROM nhan_vien WHERE EXTRACT(YEAR FROM ngay_vao_lam) = ?",
                rowMapper,
                year
        );
    }
}