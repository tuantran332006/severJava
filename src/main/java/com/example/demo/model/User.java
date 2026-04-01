package com.example.demo.model;

public class User {
    private Integer id_user;
    private String username;
    private String password;
    private String vai_tro;
    private int id_nhan_vien;

    public User() {}

    public User(Integer id_user, String username, String password, String vai_tro, int id_nhan_vien) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.vai_tro = vai_tro;
        this.id_nhan_vien = id_nhan_vien;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /** 
     * ⚠️ Lưu ý: trường password nên chứa HASH, không phải plaintext.
     * Hãy hash ở Service trước khi lưu DB (VD: BCrypt/Argon2).
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVai_tro() {
        return vai_tro;
    }

    public void setVai_tro(String vai_tro) {
        this.vai_tro = vai_tro;
    }

    public int getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(Integer id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    @Override
    public String toString() {
        // ⚠️ Không nên in password thật — thay bằng mask
        return "User{" +
                "id_user=" + id_user +
                ", username='" + username + '\'' +
                ", password='***MASKED***'" +
                ", vai_tro='" + vai_tro + '\'' +
                ", id_nhan_vien=" + id_nhan_vien +
                '}';
    }
}