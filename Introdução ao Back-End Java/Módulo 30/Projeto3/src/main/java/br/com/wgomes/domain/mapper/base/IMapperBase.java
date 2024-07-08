package br.com.wgomes.domain.mapper.base;

import java.util.List;

public interface IMapperBase<T, M> {
    T mapToEntity(M input);

    M mapToModel(T input);

    List<M> mapToModelList(List<T> input);
}
