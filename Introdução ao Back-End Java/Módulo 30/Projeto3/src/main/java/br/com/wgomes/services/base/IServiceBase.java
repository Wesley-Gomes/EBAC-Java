package br.com.wgomes.services.base;

import java.util.List;
import java.util.Optional;

public interface IServiceBase<T, I> {
    T save(T entity) throws Exception;

    T update(T entity) throws Exception;

    void delete(I id) throws Exception;

    Optional<T> findById(I id) throws Exception;

    List<T> findAll() throws Exception;
}
