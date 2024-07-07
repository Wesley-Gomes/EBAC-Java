package br.com.wgomes.services;

import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.services.base.IServiceBase;

import java.util.UUID;

public interface ICustomerService extends IServiceBase<Customer, UUID> {
    Customer findByCpf(String cpf) throws Exception;
}
