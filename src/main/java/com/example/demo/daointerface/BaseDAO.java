package com.example.demo.daointerface;

import java.util.List;

public interface BaseDAO<T, ID> {

    /** Thêm bản ghi, trả về true/false */
    boolean insert(T entity);

    /** Thêm bản ghi và trả về ID tự tăng */
    ID insertAndReturnId(T entity);

    boolean update(T entity);

    boolean delete(ID id);

    T findById(ID id);

    List<T> findAll();

    /** Kiểm tra bản ghi có tồn tại hay không */
//    boolean existsById(ID id);
}