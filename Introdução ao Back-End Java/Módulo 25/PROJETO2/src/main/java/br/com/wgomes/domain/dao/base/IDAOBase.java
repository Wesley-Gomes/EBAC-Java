package br.com.wgomes.domain.dao.base;

import java.util.List;
import java.util.Optional;

public interface IDAOBase<T, I> {
    void save(T entity);

    void update(T entity);

    void delete(I id);

    boolean existsById(I id);

    Optional<T> findById(I id);

    List<T> findAll();
}
