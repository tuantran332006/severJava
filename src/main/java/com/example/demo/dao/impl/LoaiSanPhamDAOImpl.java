package com.example.demo.dao.impl;

import com.example.demo.dao.LoaiSanPhamDAO;
import com.example.demo.model.LoaiSanPham;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LoaiSanPhamDAOImpl implements LoaiSanPhamDAO {

    private final JdbcTemplate jdbcTemplate;

    public LoaiSanPhamDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========================== ROW MAPPER ==========================

    private static class LoaiSanPhamRowMapper implements RowMapper<LoaiSanPham> {
        @Override
        public LoaiSanPham mapRow(ResultSet rs, int rowNum) throws SQLException {
            LoaiSanPham lsp = new LoaiSanPham();
            lsp.setId_loai(rs.getInt("id_loai"));
            lsp.setTen_loai(rs.getString("ten_loai"));
            lsp.setHinh_anh(rs.getBytes("hinh_anh"));
            return lsp;
        }
    }

    private final RowMapper<LoaiSanPham> rowMapper = new LoaiSanPhamRowMapper();

    // ========================== INSERT ==========================

    @Override
    public Integer insertAndReturnId(LoaiSanPham entity) {
        String sql = """
            INSERT INTO loai_san_pham (ten_loai, hinh_anh)
            VALUES (?, ?)
            RETURNING id_loai
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getTen_loai(),
                entity.getHinh_anh()
        );
    }

    @Override
    public boolean insert(LoaiSanPham entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_loai(id);
            return true;
        }
        return false;
    }

    // ========================== UPDATE / DELETE ==========================

    @Override
    public boolean update(LoaiSanPham entity) {
        String sql = """
            UPDATE loai_san_pham
            SET ten_loai = ?, hinh_anh = ?
            WHERE id_loai = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getTen_loai(),
                entity.getHinh_anh(),
                entity.getId_loai()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM loai_san_pham WHERE id_loai = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    // ========================== QUERY ==========================

    @Override
    public LoaiSanPham findById(Integer id) {
        String sql = "SELECT * FROM loai_san_pham WHERE id_loai = ?";
        List<LoaiSanPham> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<LoaiSanPham> findAll() {
        String sql = "SELECT * FROM loai_san_pham ORDER BY id_loai ASC";
        return jdbcTemplate.query(sql, rowMapper);
    }
}