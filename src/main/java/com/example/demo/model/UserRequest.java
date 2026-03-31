package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    @JsonProperty("id_user")
    @JsonAlias({"idUser"})
    private int id_user;

    @JsonProperty("username")
    private String username;

    @JsonProperty("vai_tro")
    @JsonAlias({"vaiTro", "role"})
    private String vai_tro;

    @JsonProperty("id_nhan_vien")
    @JsonAlias({"idNhanVien"})
    private int id_nhan_vien;

    public UserRequest() {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setId_nhan_vien(int id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "id_user=" + id_user +
                ", username='" + username + '\'' +
                ", vai_tro='" + vai_tro + '\'' +
                ", id_nhan_vien=" + id_nhan_vien +
                '}';
    }
}