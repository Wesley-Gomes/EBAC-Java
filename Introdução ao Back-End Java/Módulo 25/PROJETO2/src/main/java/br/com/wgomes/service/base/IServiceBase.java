package br.com.wgomes.service.base;

import br.com.wgomes.domain.exception.AlreadyExistException;
import br.com.wgomes.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface IServiceBase<T, I> {
    T save(T entity) throws AlreadyExistException;

    T update(T entity) throws NotFoundException;

    void delete(I id) throws NotFoundException;

    Optional<T> findById(I id);

    List<T> findAll();
}
