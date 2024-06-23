package br.com.wgomes.service;

import br.com.wgomes.domain.exception.NotFoundException;
import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.service.base.IServiceBase;

import java.util.UUID;

public interface ICustomerService extends IServiceBase<Customer, UUID> {
    Customer findByCpf(String cpf) throws NotFoundException;
}
