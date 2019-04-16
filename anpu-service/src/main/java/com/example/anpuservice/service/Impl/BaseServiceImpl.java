package com.example.anpuservice.service.Impl;



import com.example.anpuservice.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public abstract class BaseServiceImpl<M extends Mapper<T>, T> implements BaseService<T> {
    @Autowired
    protected M mapper;

    public M getMapper() {
        return mapper;
    }

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    @Override
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }


    @Override
    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }


    @Override
    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }


    @Override
    public List<T> selectListAll() {
        return mapper.selectAll();
    }


    @Override
    public Integer selectCount(T entity) {
        return mapper.selectCount(entity);
    }


    @Override
    public void insert(T entity) {
        mapper.insert(entity);
    }


    @Override
    public void insertSelective(T entity) {
        mapper.insertSelective(entity);
    }


    @Override
    public void delete(T entity) {
        mapper.delete(entity);
    }


    @Override
    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }


    @Override
    public void updateById(T entity) {
        mapper.updateByPrimaryKey(entity);
    }


    @Override
    public void updateSelectiveById(T entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

}
