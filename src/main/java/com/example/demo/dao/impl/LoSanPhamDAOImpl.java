package com.example.demo.dao.impl;

import com.example.demo.dao.LoSanPhamDAO;
import com.example.demo.model.LoSanPham;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public class LoSanPhamDAOImpl implements LoSanPhamDAO {

    private final JdbcTemplate jdbcTemplate;

    public LoSanPhamDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    private static class LoSanPhamRowMapper implements RowMapper<LoSanPham> {
        @Override
        public LoSanPham mapRow(ResultSet rs, int rowNum) throws SQLException {
            LoSanPham lo = new LoSanPham();
            lo.setId_lo(rs.getInt("id_lo"));
            lo.setId_sp(rs.getInt("id_sp"));
            lo.setId_phieu_nhap(rs.getInt("id_phieu_nhap"));
            lo.setSo_luong_nhap(rs.getInt("so_luong_nhap"));
            lo.setSo_luong_con(rs.getInt("so_luong_con"));
            lo.setDon_gia_nhap(rs.getDouble("don_gia_nhap"));

            if (rs.getTimestamp("ngay_nhap") != null) {
                lo.setNgay_nhap(rs.getTimestamp("ngay_nhap").toLocalDateTime());
            }
            if (rs.getTimestamp("ngay_san_xuat") != null) {
                lo.setNgay_san_xuat(rs.getTimestamp("ngay_san_xuat").toLocalDateTime());
            }
            if (rs.getTimestamp("han_su_dung") != null) {
                lo.setHan_su_dung(rs.getTimestamp("han_su_dung").toLocalDateTime());
            }
            return lo;
        }
    }

    private final RowMapper<LoSanPham> rowMapper = new LoSanPhamRowMapper();

    @Override
    public int checkSoLuongSPConTheoId(int idLo) {
        String sql = """
            SELECT so_luong_con
            FROM lo_san_pham
            WHERE id_lo = ?
            """;

        Integer result = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idLo
        );

        return result != null ? result : 0;
    }
    @Override
    public Integer insertAndReturnId(LoSanPham entity) {
        String sql = """
            INSERT INTO lo_san_pham
            (id_sp, id_phieu_nhap, so_luong_nhap, so_luong_con, don_gia_nhap,
             ngay_nhap, ngay_san_xuat, han_su_dung)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id_lo
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getId_sp(),
                entity.getId_phieu_nhap(),
                entity.getSo_luong_nhap(),
                entity.getSo_luong_con(),
                entity.getDon_gia_nhap(),
                entity.getNgay_nhap(),
                entity.getNgay_san_xuat(),
                entity.getHan_su_dung()
        );
    }
    @Override
    public boolean insert(LoSanPham entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_lo(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(LoSanPham entity) {
        String sql = """
            UPDATE lo_san_pham
            SET id_sp = ?, id_phieu_nhap = ?, so_luong_nhap = ?, so_luong_con = ?,
                don_gia_nhap = ?, ngay_nhap = ?, ngay_san_xuat = ?, han_su_dung = ?
            WHERE id_lo = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getId_sp(),
                entity.getId_phieu_nhap(),
                entity.getSo_luong_nhap(),
                entity.getSo_luong_con(),
                entity.getDon_gia_nhap(),
                entity.getNgay_nhap(),
                entity.getNgay_san_xuat(),
                entity.getHan_su_dung(),
                entity.getId_lo()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update(
                "DELETE FROM lo_san_pham WHERE id_lo = ?",
                id
        ) > 0;
    }

    @Override
    public LoSanPham findById(Integer id) {
        List<LoSanPham> list = jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE id_lo = ?",
                rowMapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<LoSanPham> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham ORDER BY id_lo DESC",
                rowMapper
        );
    }

    @Override
    public List<LoSanPham> findByDate(LocalDate date) {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE ngay_nhap::date = ?",
                rowMapper,
                date
        );
    }

    @Override
    public List<LoSanPham> findByMonth(YearMonth ym) {
        return jdbcTemplate.query(
                """
                SELECT * FROM lo_san_pham
                WHERE EXTRACT(YEAR FROM ngay_nhap) = ?
                  AND EXTRACT(MONTH FROM ngay_nhap) = ?
                """,
                rowMapper,
                ym.getYear(),
                ym.getMonthValue()
        );
    }

    @Override
    public List<LoSanPham> findByYear(int year) {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE EXTRACT(YEAR FROM ngay_nhap) = ?",
                rowMapper,
                year
        );
    }

    @Override
    public List<LoSanPham> findByExpiryDate(LocalDate date) {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE han_su_dung::date = ?",
                rowMapper,
                date
        );
    }

    @Override
    public List<LoSanPham> findByExpiryBetween(LocalDate start, LocalDate end) {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE han_su_dung::date BETWEEN ? AND ?",
                rowMapper,
                start,
                end
        );
    }

    @Override
    public List<LoSanPham> findExpiringWithinDays(int days) {
        return jdbcTemplate.query(
                """
                SELECT * FROM lo_san_pham
                WHERE han_su_dung::date
                BETWEEN CURRENT_DATE AND CURRENT_DATE + (? * INTERVAL '1 day')
                """,
                rowMapper,
                days
        );
    }

    @Override
    public List<LoSanPham> findExpired() {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE han_su_dung::date < CURRENT_DATE",
                rowMapper
        );
    }

    @Override
    public List<LoSanPham> findBySanPhamId(int idSp) {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE id_sp = ? ORDER BY id_lo DESC",
                rowMapper,
                idSp
        );
    }

    @Override
    public List<LoSanPham> findByPhieuNhapId(int idPhieuNhap) {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE id_phieu_nhap = ? ORDER BY id_lo DESC",
                rowMapper,
                idPhieuNhap
        );
    }

    @Override
    public List<LoSanPham> findConHang() {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE so_luong_con > 0 ORDER BY han_su_dung ASC",
                rowMapper
        );
    }

    @Override
    public List<LoSanPham> findConHangBySanPhamId(int idSp) {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE id_sp = ? AND so_luong_con > 0 ORDER BY han_su_dung ASC",
                rowMapper,
                idSp
        );
    }

    @Override
    public List<LoSanPham> findBySanPhamIdOrderByExpiryAsc(int idSp) {
        return jdbcTemplate.query(
                "SELECT * FROM lo_san_pham WHERE id_sp = ? ORDER BY han_su_dung ASC",
                rowMapper,
                idSp
        );
    }

    @Override
    public boolean updateSoLuongCon(int idLo, int soLuongConMoi) {
        return jdbcTemplate.update(
                "UPDATE lo_san_pham SET so_luong_con = ? WHERE id_lo = ?",
                soLuongConMoi,
                idLo
        ) > 0;
    }

    @Override
    public boolean tangSoLuongCon(int idLo, int soLuongThem) {
        return jdbcTemplate.update(
                "UPDATE lo_san_pham SET so_luong_con = so_luong_con + ? WHERE id_lo = ?",
                soLuongThem,
                idLo
        ) > 0;
    }

    @Override
    public boolean giamSoLuongCon(int idLo, int soLuongBot) {
        return jdbcTemplate.update(
                """
                UPDATE lo_san_pham
                SET so_luong_con = so_luong_con - ?
                WHERE id_lo = ? AND so_luong_con >= ?
                """,
                soLuongBot,
                idLo,
                soLuongBot
        ) > 0;
    }
    @Override
    public boolean checkLoSanPhamTonTaiTheoId(int idLo) {
        String sql = """
            SELECT EXISTS (
                SELECT 1
                FROM lo_san_pham
                WHERE id_lo = ?
            )
            """;

        Boolean exists = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                idLo
        );

        return exists != null && exists;
    }
}