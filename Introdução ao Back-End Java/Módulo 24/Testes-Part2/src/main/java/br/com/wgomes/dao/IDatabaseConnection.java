package br.com.wgomes.dao;

public interface IDatabaseConnection {
    void insert(Object object);
    void update(Object object);
    void delete(Object object);
    Object findById(Object object);
}
