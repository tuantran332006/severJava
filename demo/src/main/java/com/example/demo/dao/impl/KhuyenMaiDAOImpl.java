package com.example.demo.dao.impl;

import com.example.demo.dao.KhuyenMaiDAO;
import com.example.demo.model.KhuyenMai;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public class KhuyenMaiDAOImpl implements KhuyenMaiDAO {

    private final JdbcTemplate jdbcTemplate;

    public KhuyenMaiDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========================== ROW MAPPER ==========================

    private static class KhuyenMaiRowMapper implements RowMapper<KhuyenMai> {
        @Override
        public KhuyenMai mapRow(ResultSet rs, int rowNum) throws SQLException {
            KhuyenMai km = new KhuyenMai();
            km.setId_khuyen_mai(rs.getInt("id_khuyen_mai"));
            km.setTen_khuyen_mai(rs.getString("ten_khuyen_mai"));
            km.setPhan_tram_giam(rs.getDouble("phan_tram_giam"));

            if (rs.getTimestamp("ngay_bat_dau") != null) {
                km.setNgay_bat_dau(rs.getTimestamp("ngay_bat_dau").toLocalDateTime());
            }
            if (rs.getTimestamp("ngay_ket_thuc") != null) {
                km.setNgay_ket_thuc(rs.getTimestamp("ngay_ket_thuc").toLocalDateTime());
            }

            km.setMo_ta(rs.getString("mo_ta"));
            return km;
        }
    }

    private final RowMapper<KhuyenMai> rowMapper = new KhuyenMaiRowMapper();

    // ========================== INSERT ==========================

    @Override
    public Integer insertAndReturnId(KhuyenMai entity) {
        String sql = """
            INSERT INTO khuyen_mai
            (ten_khuyen_mai, phan_tram_giam, ngay_bat_dau, ngay_ket_thuc, mo_ta)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id_khuyen_mai
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getTen_khuyen_mai(),
                entity.getPhan_tram_giam(),
                entity.getNgay_bat_dau(),
                entity.getNgay_ket_thuc(),
                entity.getMo_ta()
        );
    }

    @Override
    public boolean insert(KhuyenMai entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_khuyen_mai(id);
            return true;
        }
        return false;
    }

    // ========================== UPDATE / DELETE ==========================

    @Override
    public boolean update(KhuyenMai entity) {
        String sql = """
            UPDATE khuyen_mai
            SET ten_khuyen_mai = ?, phan_tram_giam = ?,
                ngay_bat_dau = ?, ngay_ket_thuc = ?, mo_ta = ?
            WHERE id_khuyen_mai = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getTen_khuyen_mai(),
                entity.getPhan_tram_giam(),
                entity.getNgay_bat_dau(),
                entity.getNgay_ket_thuc(),
                entity.getMo_ta(),
                entity.getId_khuyen_mai()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM khuyen_mai WHERE id_khuyen_mai = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    // ========================== QUERY ==========================

    @Override
    public KhuyenMai findById(Integer id) {
        String sql = "SELECT * FROM khuyen_mai WHERE id_khuyen_mai = ?";
        List<KhuyenMai> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<KhuyenMai> findAll() {
        String sql = "SELECT * FROM khuyen_mai ORDER BY ngay_bat_dau DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<KhuyenMai> findByDate(LocalDate date) {
        String sql = """
            SELECT * FROM khuyen_mai
            WHERE ngay_bat_dau::date = ?
               OR ngay_ket_thuc::date = ?
        """;
        return jdbcTemplate.query(sql, rowMapper, date, date);
    }

    @Override
    public List<KhuyenMai> findByMonth(YearMonth ym) {
        String sql = """
            SELECT * FROM khuyen_mai
            WHERE (EXTRACT(YEAR FROM ngay_bat_dau) = ? AND EXTRACT(MONTH FROM ngay_bat_dau) = ?)
               OR (EXTRACT(YEAR FROM ngay_ket_thuc) = ? AND EXTRACT(MONTH FROM ngay_ket_thuc) = ?)
        """;
        return jdbcTemplate.query(
                sql,
                rowMapper,
                ym.getYear(),
                ym.getMonthValue(),
                ym.getYear(),
                ym.getMonthValue()
        );
    }

    @Override
    public List<KhuyenMai> findByYear(int year) {
        String sql = """
            SELECT * FROM khuyen_mai
            WHERE EXTRACT(YEAR FROM ngay_bat_dau) = ?
               OR EXTRACT(YEAR FROM ngay_ket_thuc) = ?
        """;
        return jdbcTemplate.query(sql, rowMapper, year, year);
    }
}