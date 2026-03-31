package com.example.demo.dao.impl;

import com.example.demo.dao.KhachHangDAO;
import com.example.demo.model.KhachHang;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class KhachHangDAOImpl implements KhachHangDAO {

    private final JdbcTemplate jdbcTemplate;

    public KhachHangDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========================== ROW MAPPER ==========================

    private static class KhachHangRowMapper implements RowMapper<KhachHang> {
        @Override
        public KhachHang mapRow(ResultSet rs, int rowNum) throws SQLException {
            KhachHang kh = new KhachHang();
            kh.setId_kh(rs.getInt("id_kh"));
            kh.setTen_kh(rs.getString("ten_kh"));
            kh.setNam_sinh_kh(rs.getInt("nam_sinh_kh"));
            kh.setDiem_thuong_kh(rs.getInt("diem_thuong_kh"));
            kh.setSo_dien_thoai(rs.getString("so_dien_thoai"));
            kh.setDia_chi(rs.getString("dia_chi"));
            kh.setGioi_tinh(rs.getString("gioi_tinh"));
            return kh;
        }
    }

    private final RowMapper<KhachHang> rowMapper = new KhachHangRowMapper();

    // ========================== INSERT ==========================

    @Override
    public Integer insertAndReturnId(KhachHang entity) {
        String sql = """
            INSERT INTO khach_hang
            (ten_kh, nam_sinh_kh, diem_thuong_kh, so_dien_thoai, dia_chi, gioi_tinh)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id_kh
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getTen_kh(),
                entity.getNam_sinh_kh(),
                entity.getDiem_thuong_kh(),
                entity.getSo_dien_thoai(),
                entity.getDia_chi(),
                entity.getGioi_tinh()
        );
    }

    @Override
    public boolean insert(KhachHang entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_kh(id);
            return true;
        }
        return false;
    }

    // ========================== UPDATE / DELETE ==========================

    @Override
    public boolean update(KhachHang entity) {
        String sql = """
            UPDATE khach_hang
            SET ten_kh = ?, nam_sinh_kh = ?, diem_thuong_kh = ?,
                so_dien_thoai = ?, dia_chi = ?, gioi_tinh = ?
            WHERE id_kh = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getTen_kh(),
                entity.getNam_sinh_kh(),
                entity.getDiem_thuong_kh(),
                entity.getSo_dien_thoai(),
                entity.getDia_chi(),
                entity.getGioi_tinh(),
                entity.getId_kh()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM khach_hang WHERE id_kh = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    // ========================== QUERY ==========================

    @Override
    public KhachHang findById(Integer id) {
        String sql = "SELECT * FROM khach_hang WHERE id_kh = ?";
        List<KhachHang> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<KhachHang> findAll() {
        String sql = "SELECT * FROM khach_hang ORDER BY id_kh DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }
}