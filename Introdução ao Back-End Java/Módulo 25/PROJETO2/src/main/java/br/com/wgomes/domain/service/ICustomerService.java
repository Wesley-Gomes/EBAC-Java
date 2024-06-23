package br.com.wgomes.domain.service;

import br.com.wgomes.domain.exption.NotFoundException;
import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.domain.service.base.IServiceBase;

import java.util.UUID;

public interface ICustomerService extends IServiceBase<Customer, UUID> {
    Customer findByCpf(String cpf) throws NotFoundException;
}
