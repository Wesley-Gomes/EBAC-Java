package br.com.wgomes.domain.dao;

import br.com.wgomes.domain.dao.base.IDAOBase;
import br.com.wgomes.domain.entity.CustomerEntity;

import java.util.Optional;
import java.util.UUID;

public interface ICustomerDAO extends IDAOBase<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByCpf(String cpf);
}
