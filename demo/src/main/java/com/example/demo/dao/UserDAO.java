package com.example.demo.dao;
import com.example.demo.model.User;
import com.example.demo.daointerface.BaseDAO;


import java.util.Optional;

public interface UserDAO extends BaseDAO<User, Integer> {

	boolean updatePassword(int idUser, String hashedPassword);

	boolean existsByUsername(String username);

	Optional<User> findByUsername(String username);

}