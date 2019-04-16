package com.example.anpuservice.service;

import java.util.List;

public interface BaseService<T> {
    T selectOne(T entity);


    T selectById(Object id);


    List<T> selectList(T entity);


    List<T> selectListAll();


    Integer selectCount(T entity);


    void insert(T entity);

    void insertSelective(T entity);

    void delete(T entity);


    void deleteById(Object id);

    void updateById(T entity);

    void updateSelectiveById(T entity);

    List<T> selectByExample(Object example);

    int selectCountByExample(Object example);
}
