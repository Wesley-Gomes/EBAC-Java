package br.com.wgomes.domain.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, I extends Serializable> {
    Optional<T> findById(I id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(I id);
}
