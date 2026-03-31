package com.example.demo.dao.impl;

import com.example.demo.dao.NhaCungCapDAO;
import com.example.demo.model.NhaCungCap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public class NhaCungCapDAOImpl implements NhaCungCapDAO {

    private final JdbcTemplate jdbcTemplate;

    public NhaCungCapDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========================== ROW MAPPER ==========================

    private static class NhaCungCapRowMapper implements RowMapper<NhaCungCap> {
        @Override
        public NhaCungCap mapRow(ResultSet rs, int rowNum) throws SQLException {
            NhaCungCap ncc = new NhaCungCap();
            ncc.setId_ncc(rs.getInt("id_ncc"));
            ncc.setTen_ncc(rs.getString("ten_ncc"));
            ncc.setSo_dien_thoai(rs.getString("so_dien_thoai"));
            ncc.setDia_chi(rs.getString("dia_chi"));
            ncc.setEmail(rs.getString("email"));

            if (rs.getTimestamp("ngay_hop_tac") != null) {
                ncc.setNgay_hop_tac(rs.getTimestamp("ngay_hop_tac").toLocalDateTime());
            }
            return ncc;
        }
    }

    private final RowMapper<NhaCungCap> rowMapper = new NhaCungCapRowMapper();

    // ========================== INSERT ==========================

    @Override
    public Integer insertAndReturnId(NhaCungCap entity) {
        String sql = """
            INSERT INTO nha_cung_cap
            (ten_ncc, so_dien_thoai, dia_chi, email, ngay_hop_tac)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id_ncc
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getTen_ncc(),
                entity.getSo_dien_thoai(),
                entity.getDia_chi(),
                entity.getEmail(),
                entity.getNgay_hop_tac()
        );
    }

    @Override
    public boolean insert(NhaCungCap entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_ncc(id);
            return true;
        }
        return false;
    }

    // ========================== UPDATE / DELETE ==========================

    @Override
    public boolean update(NhaCungCap entity) {
        String sql = """
            UPDATE nha_cung_cap
            SET ten_ncc = ?, so_dien_thoai = ?, dia_chi = ?, email = ?, ngay_hop_tac = ?
            WHERE id_ncc = ?
        """;

        return jdbcTemplate.update(
                sql,
                entity.getTen_ncc(),
                entity.getSo_dien_thoai(),
                entity.getDia_chi(),
                entity.getEmail(),
                entity.getNgay_hop_tac(),
                entity.getId_ncc()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update(
                "DELETE FROM nha_cung_cap WHERE id_ncc = ?",
                id
        ) > 0;
    }

    // ========================== QUERY ==========================

    @Override
    public NhaCungCap findById(Integer id) {
        List<NhaCungCap> list = jdbcTemplate.query(
                "SELECT * FROM nha_cung_cap WHERE id_ncc = ?",
                rowMapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<NhaCungCap> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM nha_cung_cap ORDER BY id_ncc DESC",
                rowMapper
        );
    }

    // ========================== LỌC THEO THỜI GIAN ==========================

    @Override
    public List<NhaCungCap> findByDate(LocalDate date) {
        return jdbcTemplate.query(
                "SELECT * FROM nha_cung_cap WHERE ngay_hop_tac::date = ?",
                rowMapper,
                date
        );
    }

    @Override
    public List<NhaCungCap> findByMonth(YearMonth ym) {
        return jdbcTemplate.query(
                """
                SELECT * FROM nha_cung_cap
                WHERE EXTRACT(YEAR FROM ngay_hop_tac) = ?
                  AND EXTRACT(MONTH FROM ngay_hop_tac) = ?
                """,
                rowMapper,
                ym.getYear(),
                ym.getMonthValue()
        );
    }

    @Override
    public List<NhaCungCap> findByYear(int year) {
        return jdbcTemplate.query(
                "SELECT * FROM nha_cung_cap WHERE EXTRACT(YEAR FROM ngay_hop_tac) = ?",
                rowMapper,
                year
        );
    }
}