package com.example.demo.dao.impl;

import com.example.demo.dao.SanPhamDAO;
import com.example.demo.model.SanPham;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SanPhamDAOImpl implements SanPhamDAO {

    private final JdbcTemplate jdbcTemplate;

    public SanPhamDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========================== ROW MAPPER ==========================

    private static class SanPhamRowMapper implements RowMapper<SanPham> {
        @Override
        public SanPham mapRow(ResultSet rs, int rowNum) throws SQLException {
            SanPham sp = new SanPham();
            sp.setIdSp(rs.getInt("id_sp"));
            sp.setTenSp(rs.getString("ten_sp"));
            sp.setGiaSp(rs.getDouble("gia_sp"));
            sp.setTongSoLuongSpTrongKho(rs.getInt("tong_so_luong_sp_trong_kho"));
            sp.setIdLoai(rs.getInt("id_loai"));
            sp.setDonViTinh(rs.getString("don_vi_tinh"));
            sp.setMoTa(rs.getString("mo_ta"));
            return sp;
        }
    }

    private final RowMapper<SanPham> rowMapper = new SanPhamRowMapper();

    // ========================== INSERT ==========================

    @Override
    public Integer insertAndReturnId(SanPham entity) {
        String sql = """
            INSERT INTO san_pham
            (ten_sp, gia_sp, tong_so_luong_sp_trong_kho, id_loai, don_vi_tinh, mo_ta)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id_sp
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getTenSp(),
                entity.getGiaSp(),
                entity.getTongSoLuongSpTrongKho(),
                entity.getIdLoai(),
                entity.getDonViTinh(),
                entity.getMoTa()
        );
    }

    @Override
    public boolean insert(SanPham entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setIdSp(id);
            return true;
        }
        return false;
    }

    // ========================== UPDATE / DELETE ==========================

    @Override
    public boolean update(SanPham entity) {
        String sql = """
            UPDATE san_pham
            SET ten_sp = ?, gia_sp = ?, tong_so_luong_sp_trong_kho = ?,
                id_loai = ?, don_vi_tinh = ?, mo_ta = ?
            WHERE id_sp = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getTenSp(),
                entity.getGiaSp(),
                entity.getTongSoLuongSpTrongKho(),
                entity.getIdLoai(),
                entity.getDonViTinh(),
                entity.getMoTa(),
                entity.getIdSp()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update(
                "DELETE FROM san_pham WHERE id_sp = ?",
                id
        ) > 0;
    }

    // ========================== QUERY ==========================

    @Override
    public SanPham findById(Integer id) {
        List<SanPham> list = jdbcTemplate.query(
                "SELECT * FROM san_pham WHERE id_sp = ?",
                rowMapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean existsById(int idSp) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM san_pham WHERE id_sp = ?",
                Integer.class,
                idSp
        );
        return count != null && count > 0;
    }

    @Override
    public List<SanPham> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM san_pham ORDER BY id_sp DESC",
                rowMapper
        );
    }

    @Override
    public List<SanPham> findByLoai(int idLoai) {
        return jdbcTemplate.query(
                "SELECT * FROM san_pham WHERE id_loai = ? ORDER BY id_sp DESC",
                rowMapper,
                idLoai
        );
    }

    @Override
    public List<SanPham> findByTen(String tenSp) {
        return jdbcTemplate.query(
                "SELECT * FROM san_pham WHERE ten_sp LIKE ? ORDER BY id_sp DESC",
                rowMapper,
                "%" + tenSp + "%"
        );
    }

    @Override
    public List<SanPham> findByKhoangGia(double giaMin, double giaMax) {
        return jdbcTemplate.query(
                "SELECT * FROM san_pham WHERE gia_sp BETWEEN ? AND ? ORDER BY gia_sp ASC",
                rowMapper,
                giaMin,
                giaMax
        );
    }

    // ========================== TỒN KHO ==========================

    @Override
    public boolean updateTongSoLuong(int idSp, int tongSoLuongSpTrongKho) {
        return jdbcTemplate.update(
                "UPDATE san_pham SET tong_so_luong_sp_trong_kho = ? WHERE id_sp = ?",
                tongSoLuongSpTrongKho,
                idSp
        ) > 0;
    }

    @Override
    public boolean tangSoLuong(int idSp, int soLuongThem) {
        return jdbcTemplate.update(
                "UPDATE san_pham SET tong_so_luong_sp_trong_kho = tong_so_luong_sp_trong_kho + ? WHERE id_sp = ?",
                soLuongThem,
                idSp
        ) > 0;
    }

    @Override
    public boolean giamSoLuong(int idSp, int soLuongBot) {
        return jdbcTemplate.update(
                """
                UPDATE san_pham
                SET tong_so_luong_sp_trong_kho = tong_so_luong_sp_trong_kho - ?
                WHERE id_sp = ? AND tong_so_luong_sp_trong_kho >= ?
                """,
                soLuongBot,
                idSp,
                soLuongBot
        ) > 0;
    }
}