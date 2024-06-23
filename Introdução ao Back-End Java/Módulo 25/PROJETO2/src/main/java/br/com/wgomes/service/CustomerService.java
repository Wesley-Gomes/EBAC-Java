package br.com.wgomes.service;

import br.com.wgomes.domain.dao.ICustomerDAO;
import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.domain.exception.AlreadyExistException;
import br.com.wgomes.domain.exception.NotFoundException;
import br.com.wgomes.domain.mapper.ICustomerMapper;
import br.com.wgomes.domain.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerService implements ICustomerService {
    private final ICustomerDAO customerDAO;
    private final ICustomerMapper customerMapper;

    public CustomerService(ICustomerDAO customerDAO, ICustomerMapper customerMapper) {
        this.customerDAO = customerDAO;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer findByCpf(String cpf) throws NotFoundException {
        CustomerEntity customerEntity = customerDAO.findByCpf(cpf).orElseThrow(() -> new NotFoundException("Customer not found"));
        return customerMapper.mapToModel(customerEntity);
    }

    @Override
    public Customer save(Customer entity) throws AlreadyExistException {
        Optional<CustomerEntity> optionalCustomerEntity = customerDAO.findByCpf(entity.getCpf().value());
        if (optionalCustomerEntity.isPresent()) throw new AlreadyExistException("Customer already exists");
        CustomerEntity customerEntity = customerMapper.mapToEntity(entity);
        UUID customerId = UUID.randomUUID();
        customerEntity.setCustomerId(customerId);
        customerDAO.save(customerEntity);
        return customerMapper.mapToModel(customerEntity);
    }

    @Override
    public Customer update(Customer entity) throws NotFoundException {
        if (!customerDAO.existsById(entity.getCustomerId())) throw new NotFoundException("Customer not found");
        CustomerEntity customerEntity = customerMapper.mapToEntity(entity);
        customerDAO.update(customerEntity);
        return customerMapper.mapToModel(customerEntity);
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        if (!customerDAO.existsById(id)) throw new NotFoundException("Customer not found");
        customerDAO.delete(id);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return customerDAO.findById(id).map(customerMapper::mapToModel);
    }

    @Override
    public List<Customer> findAll() {
        return customerMapper.mapToModelList(customerDAO.findAll());
    }
}
