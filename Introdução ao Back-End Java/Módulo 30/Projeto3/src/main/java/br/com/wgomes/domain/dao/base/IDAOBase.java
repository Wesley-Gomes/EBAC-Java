package br.com.wgomes.domain.dao.base;

import java.util.List;
import java.util.Optional;

public interface IDAOBase<T, I> {
    void save(T entity) throws Exception;

    void update(T entity) throws Exception;

    void delete(I id) throws Exception;

    boolean existsById(I id) throws Exception;

    Optional<T> findById(I id) throws Exception;

    List<T> findAll() throws Exception;
}
