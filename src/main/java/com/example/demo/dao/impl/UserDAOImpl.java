package com.example.demo.dao.impl;

import com.example.demo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl {

    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========================== ROW MAPPER ==========================

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId_user(rs.getInt("id_user"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setVai_tro(rs.getString("vai_tro"));
            user.setId_nhan_vien(rs.getInt("id_nhan_vien"));
            return user;
        }
    }

    private final RowMapper<User> rowMapper = new UserRowMapper();

    // ========================= INSERT =========================

    public Integer insertAndReturnId(User entity) {
        String sql = """
            INSERT INTO "user"
            (username, password, vai_tro, id_nhan_vien)
            VALUES (?, ?, ?, ?)
            RETURNING id_user
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                entity.getUsername(),
                entity.getPassword(),
                entity.getVai_tro(),
                entity.getId_nhan_vien()
        );
    }

    public boolean insert(User entity) {
        Integer id = insertAndReturnId(entity);
        if (id != null && id > 0) {
            entity.setId_user(id);
            return true;
        }
        return false;
    }

    // ========================= QUERY =========================

    public Optional<User> findByUsername(String username) {
        List<User> list = jdbcTemplate.query(
                "SELECT * FROM \"user\" WHERE username = ? LIMIT 1",
                rowMapper,
                username
        );
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public Optional<User> findById(int idUser) {
        List<User> list = jdbcTemplate.query(
                "SELECT * FROM \"user\" WHERE id_user = ?",
                rowMapper,
                idUser
        );
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public boolean existsByUsername(String username) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM \"user\" WHERE username = ?",
                Integer.class,
                username
        );
        return count != null && count > 0;
    }

    public List<User> searchByUsername(String keyword) {
        return jdbcTemplate.query(
                "SELECT * FROM \"user\" WHERE username LIKE ? ORDER BY id_user DESC",
                rowMapper,
                "%" + keyword + "%"
        );
    }

    // ========================= UPDATE =========================

    public boolean updatePassword(int idUser, String hashedPassword) {
        return jdbcTemplate.update(
                "UPDATE \"user\" SET password = ? WHERE id_user = ?",
                hashedPassword,
                idUser
        ) > 0;
    }
}